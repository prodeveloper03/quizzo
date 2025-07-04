package com.code.quizzo.repository

import com.code.quizzo.model.QuizQuestion
import com.code.quizzo.network.QuizApiService
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val api: QuizApiService
) : QuizRepository {
    override suspend fun getQuizQuestions(): List<QuizQuestion> = api.fetchQuizQuestions()
}