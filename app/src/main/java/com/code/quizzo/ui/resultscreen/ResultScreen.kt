package com.code.quizzo.ui.resultscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.code.quizzo.viewmodel.QuizViewModel

@Composable
fun ResultScreen(
    viewModel: QuizViewModel,
    onRestart: () -> Unit
) {
    val correctAnswers = viewModel.correctAnswers
    val totalQuestions = viewModel.quizQuestions.value?.size ?: 0
    val highestStreak = viewModel.highestStreak

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🎉 Quiz Complete!", fontSize = 28.sp, color = Color.White, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        Text("✅ Correct Answers: $correctAnswers", color = Color.White, fontSize = 18.sp)
        Text("📊 Total Questions: $totalQuestions", color = Color.White, fontSize = 18.sp)
        Text("🔥 Highest Streak: $highestStreak", color = Color.White, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                viewModel.resetQuiz()
                onRestart()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Restart", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}




