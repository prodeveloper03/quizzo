package com.code.quizzo.repository

import com.code.quizzo.model.QuizQuestion

interface QuizRepository {
    suspend fun getQuizQuestions(): List<QuizQuestion>
}