package com.code.quizzo.ui.quizscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StreakRow(currentStreak: Int, maxStreak: Int = 5) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
    ) {
        items(maxStreak) { index ->
            val icon = if (index < currentStreak) "🔥" else "–"
            Text(
                text = icon,
                fontSize = 20.sp,
                modifier = Modifier.size(24.dp),
                color = if (index < currentStreak) Color.Red else Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}
