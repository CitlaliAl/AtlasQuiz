package com.upchiapas.atlasquiz.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.upchiapas.atlasquiz.data.repository.QuizRepository
import com.upchiapas.atlasquiz.presentation.addcountry.AddCountryViewModel
import com.upchiapas.atlasquiz.presentation.home.HomeViewModel
import com.upchiapas.atlasquiz.presentation.quiz.QuizViewModel

class AtlasQuizViewModelFactory(
    private val repository: QuizRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository) as T
        modelClass.isAssignableFrom(QuizViewModel::class.java) -> QuizViewModel(repository) as T
        modelClass.isAssignableFrom(AddCountryViewModel::class.java) -> AddCountryViewModel(repository) as T
        else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}