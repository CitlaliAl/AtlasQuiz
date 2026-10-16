package com.upchiapas.atlasquiz.presentation.addcountry

import com.upchiapas.atlasquiz.model.Continent
import com.upchiapas.atlasquiz.model.Country

enum class FieldError {
    EMPTY,
    DUPLICATE
}

data class AddCountryUiState(
    val countryName: String = "",
    val capital: String = "",
    val continent: Continent? = null,
    val countryError: FieldError? = null,
    val capitalError: FieldError? = null,
    val continentMissing: Boolean = false,
    val lastAdded: Country? = null,
    val addedCountries: List<Country> = emptyList()
)