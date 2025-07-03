package com.code.quizzo

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.code.quizzo.navigation.NavGraph

@Composable
fun QuizzoApp() {

        val navController = rememberNavController()
        NavGraph(navController)

}