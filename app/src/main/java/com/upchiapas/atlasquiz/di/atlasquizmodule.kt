package com.upchiapas.atlasquiz.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.upchiapas.atlasquiz.data.datasource.CountriesSeedDataSource
import com.upchiapas.atlasquiz.data.datasource.UserLocalDataSource
import com.upchiapas.atlasquiz.data.repository.QuizRepository
import com.upchiapas.atlasquiz.data.repository.QuizRepositoryImpl
import com.upchiapas.atlasquiz.presentation.factory.AtlasQuizViewModelFactory

class AtlasQuizModule(
    private val context: Context
) {

    private val seedDataSource: CountriesSeedDataSource by lazy {
        CountriesSeedDataSource(context.assets)
    }

    private val dataStore: DataStore<Preferences> by lazy {
        PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile(DATASTORE_NAME)
        }
    }

    private val userDataSource: UserLocalDataSource by lazy {
        UserLocalDataSource(dataStore)
    }

    private val repository: QuizRepository by lazy {
        QuizRepositoryImpl(seedDataSource, userDataSource)
    }

    val viewModelFactory: AtlasQuizViewModelFactory by lazy {
        AtlasQuizViewModelFactory(repository)
    }

    private companion object {
        const val DATASTORE_NAME = "atlasquiz_user"
    }
}