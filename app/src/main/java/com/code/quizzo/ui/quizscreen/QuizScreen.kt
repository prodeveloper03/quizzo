package com.code.quizzo.ui.quizscreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.code.quizzo.R
import com.code.quizzo.ui.dialog.ConfirmExitDialog
import com.code.quizzo.viewmodel.QuizViewModel
import kotlinx.coroutines.delay

@Composable
fun QuizScreen(
    viewModel: QuizViewModel = hiltViewModel(),
    onQuizFinished: () -> Unit,
    onExitQuiz: () -> Unit
) {
    val totalQuestions = viewModel.quizQuestions.value?.size ?: 0
    val currentQuestion = viewModel.getCurrentQuestion()

    var selectedAnswerIndex by remember { mutableStateOf<Int?>(null) }
    var isAnswered by remember { mutableStateOf(false) }
    var isCorrectAnswer by remember { mutableStateOf(false) }
    var isSkip by remember { mutableStateOf(false) }
    var shouldNavigateToResult by remember { mutableStateOf(false) }
    var showExitDialog by remember { mutableStateOf(false) }
    var shouldExitWithDelay by remember { mutableStateOf(false) }

    var showStreakAnimation by remember { mutableStateOf(false) }
    var lastStreakMilestone by remember { mutableStateOf(0) }

    BackHandler {
        showExitDialog = true
    }

    LaunchedEffect(isAnswered) {
        if (isAnswered) {
            delay(800)
            val isLast = viewModel.submitAnswer(if (isSkip) false else isCorrectAnswer)

            val newStreak = viewModel.currentStreak
            if (newStreak in listOf(3, 5, 7) && newStreak != lastStreakMilestone) {
                lastStreakMilestone = newStreak
                showStreakAnimation = true
            }

            selectedAnswerIndex = null
            isAnswered = false
            isSkip = false

            if (isLast) {
                shouldNavigateToResult = true
            }
        }
    }

    LaunchedEffect(shouldNavigateToResult) {
        if (shouldNavigateToResult) {
            onQuizFinished()
        }
    }

    LaunchedEffect(shouldExitWithDelay) {
        if (shouldExitWithDelay) {
            delay(500)
            onExitQuiz()
        }
    }

    if (viewModel.currentIndex >= totalQuestions) {
        onQuizFinished()
        return
    }

    if (currentQuestion == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            StreakRow(currentStreak = viewModel.currentStreak.coerceIn(0, 5))

            if (viewModel.currentStreak >= 3) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${viewModel.currentStreak} questions streak achieved !!",
                        color = Color.White,
                        fontSize = 14.sp
                    )

                    Text(
                        text = when (viewModel.currentStreak) {
                            3 -> "Nice! You're on a streak!"
                            5 -> "🔥 You're crushing it!"
                            9 -> "🏆 You're unstoppable!"
                            else -> ""
                        },
                        color = Color.Yellow,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        item {
            Text(
                text = "Question ${viewModel.currentIndex + 1} of $totalQuestions",
                color = Color.White,
                fontSize = 16.sp
            )
        }

        item {
            LinearProgressIndicator(
                progress = (viewModel.currentIndex + 1).toFloat() / totalQuestions,
                modifier = Modifier.fillMaxWidth(),
                color = Color.White
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = currentQuestion.question,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        items(currentQuestion.options.size) { index ->
            val option = currentQuestion.options[index]
            val isCorrect = index == currentQuestion.correctOptionIndex
            val isSelected = index == selectedAnswerIndex

            val backgroundColor = if (isAnswered && !isSkip) {
                when {
                    isSelected && isCorrect -> Color(0xFF4CAF50)
                    isSelected && !isCorrect -> Color(0xFFF44336)
                    !isSelected && isCorrect -> Color(0xFF4CAF50)
                    else -> Color.DarkGray
                }
            } else {
                Color.DarkGray
            }

            Button(
                onClick = {
                    if (!isAnswered) {
                        selectedAnswerIndex = index
                        isCorrectAnswer = isCorrect
                        isAnswered = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(50)
            ) {
                Text(text = option, color = Color.White)
            }
        }

        item {
            Spacer(modifier = Modifier.height(84.dp))

            Button(
                onClick = {
                    if (!isAnswered) {
                        selectedAnswerIndex = null
                        isSkip = true
                        isAnswered = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(50)
            ) {
                Text("Skip", color = Color.White)
            }
        }
    }

    if (showExitDialog) {
        ConfirmExitDialog(
            onDismiss = { showExitDialog = false },
            onConfirm = {
                viewModel.resetQuiz()
                showExitDialog = false
                shouldExitWithDelay = true
            }
        )
    }

    if (showStreakAnimation) {
        val animationRes = R.raw.congrats

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationRes))
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = 2,
            speed = 0.5f
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
                .clickable { showStreakAnimation = false },
            contentAlignment = Alignment.Center
        ) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(300.dp)
            )
        }

        LaunchedEffect(Unit) {
            delay(2500)
            showStreakAnimation = false
        }
    }
}






