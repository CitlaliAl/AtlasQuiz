package com.upchiapas.atlasquiz.data.repository

import com.upchiapas.atlasquiz.model.Country
import com.upchiapas.atlasquiz.model.UserProgress
import kotlinx.coroutines.flow.Flow

interface QuizRepository {

    val countries: Flow<List<Country>>

    val progress: Flow<UserProgress>

    suspend fun getCountries(): List<Country>

    suspend fun addCountry(country: Country)

    suspend fun saveQuizResult(points: Int)
}