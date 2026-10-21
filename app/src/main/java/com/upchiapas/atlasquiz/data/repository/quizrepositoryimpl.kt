package com.upchiapas.atlasquiz.data.repository

import com.upchiapas.atlasquiz.data.datasource.CountriesSeedDataSource
import com.upchiapas.atlasquiz.data.datasource.UserLocalDataSource
import com.upchiapas.atlasquiz.model.Country
import com.upchiapas.atlasquiz.model.UserProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class QuizRepositoryImpl(
    private val seedDataSource: CountriesSeedDataSource,
    private val userDataSource: UserLocalDataSource
) : QuizRepository {

    override val countries: Flow<List<Country>> =
        userDataSource.addedCountries.map { added ->
            seedDataSource.getCountries() + added
        }

    override val progress: Flow<UserProgress> = userDataSource.progress

    override suspend fun getCountries(): List<Country> = countries.first()

    override suspend fun addCountry(country: Country) {
        userDataSource.addCountry(country)
    }

    override suspend fun saveQuizResult(points: Int) {
        userDataSource.addQuizResult(points)
    }
}