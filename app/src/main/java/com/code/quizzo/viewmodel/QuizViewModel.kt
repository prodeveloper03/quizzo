package com.code.quizzo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.quizzo.commonutil.ApiResponse
import com.code.quizzo.model.QuizQuestion
import com.code.quizzo.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repository: QuizRepository
) : ViewModel() {

    private val _quizQuestions = MutableLiveData<List<QuizQuestion>>()
    val quizQuestions: LiveData<List<QuizQuestion>> = _quizQuestions

    private val _quizState = MutableLiveData<ApiResponse<List<QuizQuestion>>>()
    val quizState: LiveData<ApiResponse<List<QuizQuestion>>> = _quizState

    init {
        fetchQuestions()
    }

    private fun fetchQuestions() {
        viewModelScope.launch {
            _quizState.value = ApiResponse.Loading
            try {
                val result = repository.getQuizQuestions()
                _quizQuestions.value = result
                _quizState.value = ApiResponse.Success(result)
            } catch (e: Exception) {
                Log.e("QuizViewModel", "Error fetching questions: ${e.message}", e)
                _quizState.value = ApiResponse.Error(e.message)
            }
        }
    }
}

