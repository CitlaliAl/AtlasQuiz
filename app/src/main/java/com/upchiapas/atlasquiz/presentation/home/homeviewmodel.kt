package com.upchiapas.atlasquiz.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upchiapas.atlasquiz.data.repository.QuizRepository
import com.upchiapas.atlasquiz.model.Continent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: QuizRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        observeRepository()
    }

    fun onContinentSelected(continent: Continent) {
        _uiState.update { current -> current.copy(selectedContinent = continent) }
    }

    private fun observeRepository() {
        viewModelScope.launch {
            combine(repository.countries, repository.progress) { countries, progress ->
                Pair(countries, progress)
            }.collect { (countries, progress) ->
                _uiState.update { current ->
                    current.copy(
                        countryCounts = Continent.entries.associateWith { continent ->
                            countries.count { it.continent == continent }
                        },
                        quizzesCompleted = progress.quizzesCompleted,
                        totalPoints = progress.totalPoints
                    )
                }
            }
        }
    }
}