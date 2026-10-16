package com.upchiapas.atlasquiz.presentation.quiz

import com.upchiapas.atlasquiz.model.Continent
import com.upchiapas.atlasquiz.model.Country
import com.upchiapas.atlasquiz.model.QuizRules

data class QuizQuestion(
    val country: Country,
    val options: List<String>
)

data class AnswerRecord(
    val countryName: String,
    val correctCapital: String,
    val selectedCapital: String,
    val isCorrect: Boolean
)

enum class ResultLevel {
    EXCELLENT,
    GOOD,
    KEEP_PRACTICING
}

data class QuizUiState(
    val continent: Continent? = null,
    val questions: List<QuizQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedCapital: String? = null,
    val score: Int = 0,
    val answers: List<AnswerRecord> = emptyList(),
    val isFinished: Boolean = false
) {
    val currentQuestion: QuizQuestion?
        get() = questions.getOrNull(currentIndex)

    val isAnswered: Boolean
        get() = selectedCapital != null

    val isAnswerCorrect: Boolean
        get() = selectedCapital != null && selectedCapital == currentQuestion?.country?.capital

    val isLastQuestion: Boolean
        get() = currentIndex == questions.lastIndex

    val progress: Float
        get() = if (questions.isEmpty()) 0f else (currentIndex + 1f) / questions.size

    val correctCount: Int
        get() = answers.count { it.isCorrect }

    val percentage: Int
        get() = if (questions.isEmpty()) 0 else correctCount * 100 / questions.size

    val resultLevel: ResultLevel
        get() = when {
            percentage == 100 -> ResultLevel.EXCELLENT
            percentage >= QuizRules.PASSING_PERCENTAGE -> ResultLevel.GOOD
            else -> ResultLevel.KEEP_PRACTICING
        }
}