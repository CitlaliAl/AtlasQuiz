package com.upchiapas.atlasquiz.presentation.addcountry

import androidx.lifecycle.ViewModel
import com.upchiapas.atlasquiz.model.Continent
import com.upchiapas.atlasquiz.model.Country
import com.upchiapas.atlasquiz.presentation.provisional.ProvisionalCatalog
import java.text.Normalizer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddCountryViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AddCountryUiState())
    val uiState: StateFlow<AddCountryUiState> = _uiState.asStateFlow()

    fun onCountryNameChange(value: String) {
        _uiState.update { current ->
            current.copy(countryName = value, countryError = null, lastAdded = null)
        }
    }

    fun onCapitalChange(value: String) {
        _uiState.update { current ->
            current.copy(capital = value, capitalError = null, lastAdded = null)
        }
    }

    fun onContinentSelected(continent: Continent) {
        _uiState.update { current ->
            current.copy(continent = continent, continentMissing = false, lastAdded = null)
        }
    }

    fun onSaveClick() {
        _uiState.update { current ->
            val name = current.countryName.trim()
            val capital = current.capital.trim()
            val continent = current.continent

            val countryError = when {
                name.isEmpty() -> FieldError.EMPTY
                isDuplicate(name, current.addedCountries) -> FieldError.DUPLICATE
                else -> null
            }
            val capitalError = if (capital.isEmpty()) FieldError.EMPTY else null

            if (countryError != null || capitalError != null || continent == null) {
                current.copy(
                    countryError = countryError,
                    capitalError = capitalError,
                    continentMissing = continent == null,
                    lastAdded = null
                )
            } else {
                val country = Country(
                    id = "temp_" + normalize(name),
                    name = name,
                    capital = capital,
                    continent = continent
                )
                current.copy(
                    countryName = "",
                    capital = "",
                    continent = null,
                    countryError = null,
                    capitalError = null,
                    continentMissing = false,
                    lastAdded = country,
                    addedCountries = current.addedCountries + country
                )
            }
        }
    }

    private fun isDuplicate(name: String, addedCountries: List<Country>): Boolean {
        val target = normalize(name)
        return (ProvisionalCatalog.countries + addedCountries).any { country ->
            normalize(country.name) == target
        }
    }

    private fun normalize(text: String): String =
        Normalizer.normalize(text.trim(), Normalizer.Form.NFD)
            .replace(DIACRITICS, "")
            .lowercase()

    private companion object {
        val DIACRITICS = Regex("\\p{Mn}+")
    }
}