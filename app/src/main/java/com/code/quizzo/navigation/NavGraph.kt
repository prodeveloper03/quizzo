package com.code.quizzo.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.code.quizzo.commonutil.ApiResponse
import com.code.quizzo.ui.home.HomeScreen
import com.code.quizzo.ui.quizscreen.QuizScreen
import com.code.quizzo.ui.resultscreen.ResultScreen
import com.code.quizzo.ui.splashscreen.SplashScreen
import com.code.quizzo.viewmodel.QuizViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(navController: NavHostController) {
    val viewModel: QuizViewModel = hiltViewModel()
    val quizState by viewModel.quizState.observeAsState(ApiResponse.Loading)

    AnimatedNavHost(
        navController = navController,
        startDestination = "splash",
        enterTransition = {
            slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn(animationSpec = tween(800))
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut(animationSpec = tween(800))
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn(animationSpec = tween(800))
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut(animationSpec = tween(800))
        }
    ) {

        // Splash Screen
        composable("splash") {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
            )
        }

        // Home Screen
        composable("home") {
            HomeScreen(
                onStartQuiz = {
                    navController.navigate("quiz")
                },
                quizState = quizState,
                onRetry = {
                    viewModel.fetchQuestions()
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
                },
                onExitQuiz = {
                    navController.popBackStack("home", inclusive = false)
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
                onGoToHome = {
                    navController.popBackStack("home", inclusive = false)
                }
            )
        }
    }
}

