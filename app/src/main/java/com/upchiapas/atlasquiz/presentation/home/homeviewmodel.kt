package com.upchiapas.atlasquiz.presentation.home

import androidx.lifecycle.ViewModel
import com.upchiapas.atlasquiz.model.Continent
import com.upchiapas.atlasquiz.model.QuizRules
import com.upchiapas.atlasquiz.presentation.provisional.ProvisionalCatalog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        HomeUiState(
            asiaCount = ProvisionalCatalog.countriesOf(Continent.ASIA).size,
            europeCount = ProvisionalCatalog.countriesOf(Continent.EUROPE).size
        )
    )
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onContinentSelected(continent: Continent) {
        val available = ProvisionalCatalog.countriesOf(continent).size
        val enough = available >= QuizRules.QUESTIONS_PER_QUIZ

        _uiState.update { current ->
            current.copy(
                selectedContinent = continent,
                canStart = enough,
                needsMoreCountries = !enough
            )
        }
    }
}