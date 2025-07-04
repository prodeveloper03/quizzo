package com.code.quizzo.ui.quizscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.code.quizzo.viewmodel.QuizViewModel
import kotlinx.coroutines.delay

@Composable
fun QuizScreen(
    viewModel: QuizViewModel = hiltViewModel(),
    onQuizFinished: () -> Unit
) {
    val totalQuestions = viewModel.quizQuestions.value?.size ?: 0
    val currentQuestion = viewModel.getCurrentQuestion()

    var selectedAnswerIndex by remember { mutableStateOf<Int?>(null) }
    var isAnswered by remember { mutableStateOf(false) }
    var shouldNavigateToResult by remember { mutableStateOf(false) }


    LaunchedEffect(shouldNavigateToResult) {
        if (shouldNavigateToResult) {
            onQuizFinished()
        }
    }

    if (viewModel.currentIndex >= totalQuestions) {
        // fallback, just in case
        onQuizFinished()
        return
    }

    if (currentQuestion == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    LaunchedEffect(isAnswered) {
        if (isAnswered) {
            delay(800)
            selectedAnswerIndex = null
            isAnswered = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Question ${viewModel.currentIndex + 1} of $totalQuestions",
            color = Color.White,
            fontSize = 16.sp
        )

        LinearProgressIndicator(
            progress = (viewModel.currentIndex + 1).toFloat() / totalQuestions,
            modifier = Modifier.fillMaxWidth(),
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = currentQuestion.question,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        currentQuestion.options.forEachIndexed { index, option ->
            val isCorrect = index == currentQuestion.correctOptionIndex
            val isSelected = index == selectedAnswerIndex

            val backgroundColor = when {
                isAnswered && isSelected && isCorrect -> Color(0xFF4CAF50)
                isAnswered && isSelected && !isCorrect -> Color(0xFFF44336)
                isAnswered && !isSelected && isCorrect -> Color(0xFF4CAF50)
                else -> Color.DarkGray
            }

            Button(
                onClick = {
                    if (!isAnswered) {
                        selectedAnswerIndex = index
                        isAnswered = true
                        val isLast = viewModel.submitAnswer(isCorrect)
                        if (isLast) {
                            shouldNavigateToResult = true
                        }
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

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (!isAnswered) {
                    selectedAnswerIndex = null
                    isAnswered = true
                    val isLast = viewModel.submitAnswer(false)
                    if (isLast) {
                        shouldNavigateToResult = true
                    }
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



