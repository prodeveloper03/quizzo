package com.code.quizzo.network

import com.code.quizzo.model.QuizQuestion
import retrofit2.http.GET


interface QuizApiService {
    @GET("53846277a8fcb034e482906ccc0d12b2/raw")
    suspend fun fetchQuizQuestions(): List<QuizQuestion>
}
