package com.upchiapas.atlasquiz.presentation.quiz

import androidx.lifecycle.ViewModel
import com.upchiapas.atlasquiz.model.Continent
import com.upchiapas.atlasquiz.model.Country
import com.upchiapas.atlasquiz.model.QuizRules
import com.upchiapas.atlasquiz.presentation.provisional.ProvisionalCatalog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class QuizViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    fun startQuiz(continent: Continent) {
        val pool = ProvisionalCatalog.countriesOf(continent)
        val questions = if (pool.size >= QuizRules.QUESTIONS_PER_QUIZ) {
            pool.shuffled()
                .take(QuizRules.QUESTIONS_PER_QUIZ)
                .map { country -> buildQuestion(country, pool) }
        } else {
            emptyList()
        }
        _uiState.value = QuizUiState(continent = continent, questions = questions)
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
        _uiState.update { current ->
            when {
                !current.isAnswered -> current
                current.isLastQuestion -> current.copy(isFinished = true)
                else -> current.copy(
                    currentIndex = current.currentIndex + 1,
                    selectedCapital = null
                )
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