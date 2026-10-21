package com.upchiapas.atlasquiz.di

import com.upchiapas.atlasquiz.data.datasource.CountriesSeedDataSource
import com.upchiapas.atlasquiz.data.datasource.UserLocalDataSource
import com.upchiapas.atlasquiz.data.repository.QuizRepository
import com.upchiapas.atlasquiz.data.repository.QuizRepositoryImpl
import com.upchiapas.atlasquiz.presentation.factory.AtlasQuizViewModelFactory

class AtlasQuizModule {

    private val seedDataSource: CountriesSeedDataSource by lazy {
        CountriesSeedDataSource()
    }

    private val userDataSource: UserLocalDataSource by lazy {
        UserLocalDataSource()
    }

    private val repository: QuizRepository by lazy {
        QuizRepositoryImpl(seedDataSource, userDataSource)
    }

    val viewModelFactory: AtlasQuizViewModelFactory by lazy {
        AtlasQuizViewModelFactory(repository)
    }
}