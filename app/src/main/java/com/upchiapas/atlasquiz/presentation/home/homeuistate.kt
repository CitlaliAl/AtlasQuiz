package com.upchiapas.atlasquiz.presentation.home

import com.upchiapas.atlasquiz.model.Continent

data class HomeUiState(
    val selectedContinent: Continent? = null,
    val asiaCount: Int = 0,
    val europeCount: Int = 0,
    val quizzesCompleted: Int = 0,
    val totalPoints: Int = 0,
    val canStart: Boolean = false,
    val needsMoreCountries: Boolean = false
)