package com.code.quizzo.ui.home


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.code.quizzo.viewmodel.QuizViewModel


@Composable
fun HomeScreen(viewModel: QuizViewModel = hiltViewModel()) {
    val questions by viewModel.quizQuestions.observeAsState()

    questions?.let { list ->
        Column {
            Text("Total: ${list.size}")
            list.forEach { question ->
                Text(text = question.question)
            }
        }
    }

}

