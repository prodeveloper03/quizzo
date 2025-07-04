package com.code.quizzo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.code.quizzo.commonutil.ApiResponse
import com.code.quizzo.ui.home.HomeScreen
import com.code.quizzo.ui.quizscreen.QuizScreen
import com.code.quizzo.ui.splashscreen.SplashScreen
import com.code.quizzo.viewmodel.QuizViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    val viewModel: QuizViewModel = hiltViewModel()
    val quizState by viewModel.quizState.observeAsState(ApiResponse.Loading)
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(
                quizState = quizState,
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("home") {
            HomeScreen(
                onStartQuiz = {
                    navController.navigate("quiz")
                }
            )
        }

        composable("quiz") {
          QuizScreen()
        }
    }
}