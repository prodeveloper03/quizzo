package com.code.quizzo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable





private val DarkColorScheme = darkColorScheme(
    primary = GreenPrimary,
    background = Black,
    surface = Black,
    onPrimary = White,
    onBackground = White,
    onSurface = White
)

@Composable
fun QuizAppTheme(
    useDarkTheme: Boolean = true, // force dark mode always
    content: @Composable () -> Unit
) {
    val colors = DarkColorScheme

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}