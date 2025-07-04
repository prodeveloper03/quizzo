package com.code.quizzo.ui.splashscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.code.quizzo.commonutil.ApiResponse
import com.code.quizzo.model.QuizQuestion
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    quizState: ApiResponse<List<QuizQuestion>>,
    onNavigateToHome: () -> Unit
) {

    LaunchedEffect(quizState) {
        if (quizState is ApiResponse.Success) {
            delay(2000) // as we are only calling one API right now
            onNavigateToHome()
        }
    }

    // Show loading UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A)),
        contentAlignment = Alignment.Center
    ) {
        when (quizState) {
            is ApiResponse.Loading -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = Color.White)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Loading quiz...",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }

            is ApiResponse.Error -> {
                Text(
                    text = "Failed to load quiz: ${quizState.message ?: "Unknown error"}",
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }

            is ApiResponse.Success -> {
                // Already handled by LaunchedEffect
            }
        }
    }
}



//@Preview
//@Composable
//fun PreviewSplashScreen(){
//    SplashScreen()
//}