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

    private var _currentIndex = 0
    val currentIndex: Int get() = _currentIndex

    private var _correctAnswers = 0
    val correctAnswers: Int get() = _correctAnswers

    private var _currentStreak = 0
    private var _highestStreak = 0
    val highestStreak: Int get() = _highestStreak

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

    fun submitAnswer(isCorrect: Boolean): Boolean {
        if (isCorrect) {
            _correctAnswers++
            _currentStreak++
            _highestStreak = maxOf(_highestStreak, _currentStreak)
        } else {
            _currentStreak = 0
        }

        _currentIndex++

        return isLastQuestion()
    }

    fun getCurrentQuestion(): QuizQuestion? {
        return _quizQuestions.value?.getOrNull(_currentIndex)
    }

    fun isLastQuestion(): Boolean {
        val total = _quizQuestions.value?.size ?: 0
        return _currentIndex >= total
    }

    fun resetQuiz() {
        _currentIndex = 0
        _correctAnswers = 0
        _currentStreak = 0
        _highestStreak = 0
    }
}

