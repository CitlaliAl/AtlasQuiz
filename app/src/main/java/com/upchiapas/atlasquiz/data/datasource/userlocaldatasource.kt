package com.upchiapas.atlasquiz.data.datasource

import com.upchiapas.atlasquiz.model.Country
import com.upchiapas.atlasquiz.model.UserProgress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserLocalDataSource {

    private val _progress = MutableStateFlow(UserProgress())
    val progress: StateFlow<UserProgress> = _progress.asStateFlow()

    private val _addedCountries = MutableStateFlow<List<Country>>(emptyList())
    val addedCountries: StateFlow<List<Country>> = _addedCountries.asStateFlow()

    fun addCountry(country: Country) {
        _addedCountries.update { current -> current + country }
    }

    fun addQuizResult(points: Int) {
        _progress.update { current ->
            current.copy(
                quizzesCompleted = current.quizzesCompleted + 1,
                totalPoints = current.totalPoints + points
            )
        }
    }
}