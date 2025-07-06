package com.code.quizzo.ui.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.code.quizzo.commonutil.ApiResponse
import com.code.quizzo.model.QuizQuestion
import com.code.quizzo.ui.dialog.ErrorDialog


@Composable
fun HomeScreen(
    onStartQuiz: () -> Unit,
    quizState: ApiResponse<List<QuizQuestion>>,
    onRetry: () -> Unit
) {
    var showErrorDialog by remember { mutableStateOf(false) }

    LaunchedEffect(quizState) {
        if (quizState is ApiResponse.Error) {
            showErrorDialog = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A2E)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Text(
                text = "Are you ready for the challenge?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // Start Quiz Button
            Button(
                onClick = onStartQuiz,
                enabled = quizState is ApiResponse.Success,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6C63FF)),
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .height(56.dp)
                    .width(200.dp)
            ) {
                Text(
                    text = "Start Quiz",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }


            if (quizState is ApiResponse.Loading) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        if (showErrorDialog) {
            ErrorDialog(
                onDismiss = { showErrorDialog = false },
                onTryAgain = {
                    showErrorDialog = false
                    onRetry()
                }
            )
        }
    }
}


//@Composable
//@Preview
//fun previewHomeScreen(){
//    HomeScreen {  }
//}


