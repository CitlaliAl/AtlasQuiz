package com.upchiapas.atlasquiz.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upchiapas.atlasquiz.data.repository.QuizRepository
import com.upchiapas.atlasquiz.model.Continent
import com.upchiapas.atlasquiz.model.Country
import com.upchiapas.atlasquiz.model.QuizRules
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizViewModel(
    private val repository: QuizRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    fun startQuiz(continent: Continent) {
        _uiState.value = QuizUiState(continent = continent)
        viewModelScope.launch {
            val pool = repository.getCountries().filter { it.continent == continent }
            val questions = if (pool.size >= QuizRules.QUESTIONS_PER_QUIZ) {
                pool.shuffled()
                    .take(QuizRules.QUESTIONS_PER_QUIZ)
                    .map { country -> buildQuestion(country, pool) }
            } else {
                emptyList()
            }
            _uiState.update { current -> current.copy(questions = questions) }
        }
    }

    fun restartQuiz() {
        _uiState.value.continent?.let { startQuiz(it) }
    }

    fun onAnswerSelected(capital: String) {
        _uiState.update { current ->
            val question = current.currentQuestion
            if (question == null || current.isAnswered) {
                current
            } else {
                val isCorrect = capital == question.country.capital
                current.copy(
                    selectedCapital = capital,
                    score = if (isCorrect) {
                        current.score + QuizRules.POINTS_PER_CORRECT
                    } else {
                        current.score
                    },
                    answers = current.answers + AnswerRecord(
                        countryName = question.country.name,
                        correctCapital = question.country.capital,
                        selectedCapital = capital,
                        isCorrect = isCorrect
                    )
                )
            }
        }
    }

    fun onContinue() {
        val current = _uiState.value
        if (!current.isAnswered || current.isFinished) return //evita guardar el resultado dos veces si tocas “Ver resultados” muy rápido

        if (current.isLastQuestion) {
            _uiState.update { it.copy(isFinished = true) }
            viewModelScope.launch { repository.saveQuizResult(current.score) }
        } else {
            _uiState.update {
                it.copy(currentIndex = it.currentIndex + 1, selectedCapital = null)
            }
        }
    }

    private fun buildQuestion(country: Country, pool: List<Country>): QuizQuestion {
        val distractors = pool
            .map { it.capital }
            .distinct()
            .filter { it != country.capital }
            .shuffled()
            .take(QuizRules.OPTIONS_PER_QUESTION - 1)
        return QuizQuestion(
            country = country,
            options = (distractors + country.capital).shuffled()
        )
    }
}