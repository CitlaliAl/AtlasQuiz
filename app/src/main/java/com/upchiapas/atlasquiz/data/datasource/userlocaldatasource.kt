package com.upchiapas.atlasquiz.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.upchiapas.atlasquiz.data.mapper.toCountries
import com.upchiapas.atlasquiz.data.mapper.toJsonString
import com.upchiapas.atlasquiz.model.Country
import com.upchiapas.atlasquiz.model.UserProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class UserLocalDataSource(
    private val dataStore: DataStore<Preferences> //dataStore.data es un Flow que emite cada vez que algo cambia en disco
) {

    val progress: Flow<UserProgress> = dataStore.data
        .map { preferences ->
            UserProgress(
                quizzesCompleted = preferences[QUIZZES_COMPLETED] ?: 0,
                totalPoints = preferences[TOTAL_POINTS] ?: 0
            )
        }
        .distinctUntilChanged()

    val addedCountries: Flow<List<Country>> = dataStore.data
        .map { preferences -> readCountries(preferences) }
        .distinctUntilChanged()

    suspend fun addCountry(country: Country) {
        dataStore.edit { preferences ->
            preferences[ADDED_COUNTRIES] = (readCountries(preferences) + country).toJsonString()
        }
    } //dataStore.edit { } escribe de forma segura y atómica, y por eso las dos funciones de escritura son suspend

    suspend fun addQuizResult(points: Int) {
        dataStore.edit { preferences ->
            preferences[QUIZZES_COMPLETED] = (preferences[QUIZZES_COMPLETED] ?: 0) + 1
            preferences[TOTAL_POINTS] = (preferences[TOTAL_POINTS] ?: 0) + points
        }
    }

    private fun readCountries(preferences: Preferences): List<Country> =
        preferences[ADDED_COUNTRIES]?.toCountries() ?: emptyList()

    private companion object {
        val QUIZZES_COMPLETED = intPreferencesKey("quizzes_completed")
        val TOTAL_POINTS = intPreferencesKey("total_points")
        val ADDED_COUNTRIES = stringPreferencesKey("added_countries")
    }
}