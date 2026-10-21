package com.upchiapas.atlasquiz.presentation.home

import com.upchiapas.atlasquiz.model.Continent
import com.upchiapas.atlasquiz.model.QuizRules

data class HomeUiState(
    //canStart y needsMoreCountries se calculan a partir de los demás datos. Así nunca quedan desactualizados cuando cambian los conteos
    val selectedContinent: Continent? = null,
    val countryCounts: Map<Continent, Int> = emptyMap(),
    val quizzesCompleted: Int = 0,
    val totalPoints: Int = 0
) {
    private val selectedCount: Int
        get() = selectedContinent?.let { countryCounts[it] } ?: 0

    val canStart: Boolean
        get() = selectedContinent != null && selectedCount >= QuizRules.QUESTIONS_PER_QUIZ

    val needsMoreCountries: Boolean
        get() = selectedContinent != null && selectedCount < QuizRules.QUESTIONS_PER_QUIZ
}