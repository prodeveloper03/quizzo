package com.code.quizzo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.code.quizzo.commonutil.ApiResponse
import com.code.quizzo.ui.home.HomeScreen
import com.code.quizzo.ui.quizscreen.QuizScreen
import com.code.quizzo.ui.resultscreen.ResultScreen
import com.code.quizzo.ui.splashscreen.SplashScreen
import com.code.quizzo.viewmodel.QuizViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    val viewModel: QuizViewModel = hiltViewModel()
    val quizState by viewModel.quizState.observeAsState(ApiResponse.Loading)

    NavHost(navController = navController, startDestination = "splash") {

        // Splash Screen
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

        // Home Screen
        composable("home") {
            HomeScreen(
                onStartQuiz = {
                    navController.navigate("quiz")
                }
            )
        }

        // Quiz Screen
        composable("quiz") {
            QuizScreen(
                viewModel = viewModel,
                onQuizFinished = {
                    navController.navigate(
                        "result/${viewModel.correctAnswers}/${viewModel.quizQuestions.value?.size ?: 0}/${viewModel.highestStreak}"
                    ) {
                        popUpTo("quiz") { inclusive = true }
                    }
                }
            )
        }

        // Result Screen
        composable(
            "result/{correct}/{total}/{streak}",
            arguments = listOf(
                navArgument("correct") { type = NavType.IntType },
                navArgument("total") { type = NavType.IntType },
                navArgument("streak") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val correct = backStackEntry.arguments?.getInt("correct") ?: 0
            val total = backStackEntry.arguments?.getInt("total") ?: 0
            val streak = backStackEntry.arguments?.getInt("streak") ?: 0

            ResultScreen(
                viewModel = viewModel,
                onRestart = {
                    viewModel.resetQuiz()
                    navController.navigate("home") {
                        popUpTo("result") { inclusive = true }
                    }
                },
                onClose = {
                    navController.navigate("home"){
                        popUpTo("result") { inclusive = true }
                    }
                }
            )
        }
    }
}

