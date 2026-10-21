package com.upchiapas.atlasquiz.presentation.addcountry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upchiapas.atlasquiz.R
import com.upchiapas.atlasquiz.model.Continent
import com.upchiapas.atlasquiz.presentation.components.ContinentDropdown
import com.upchiapas.atlasquiz.presentation.components.CountryListItem
import com.upchiapas.atlasquiz.presentation.components.FeedbackBanner
import com.upchiapas.atlasquiz.presentation.components.FormTextField
import com.upchiapas.atlasquiz.presentation.components.ScreenHeader
import com.upchiapas.atlasquiz.presentation.mapper.nameRes

@Composable
fun AddCountryScreen(
    onBack: () -> Unit,
    viewModel: AddCountryViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    AddCountryContent(
        state = state,
        onCountryNameChange = viewModel::onCountryNameChange,
        onCapitalChange = viewModel::onCapitalChange,
        onContinentSelected = viewModel::onContinentSelected,
        onSaveClick = viewModel::onSaveClick,
        onBackClick = onBack
    )
}

@Composable
fun AddCountryContent(
    state: AddCountryUiState,
    onCountryNameChange: (String) -> Unit,
    onCapitalChange: (String) -> Unit,
    onContinentSelected: (Continent) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
        ScreenHeader(
            title = stringResource(R.string.add_country_title),
            subtitle = stringResource(R.string.add_country_subtitle),
            onBackClick = onBackClick
        )

        FormTextField(
            value = state.countryName,
            onValueChange = onCountryNameChange,
            label = stringResource(R.string.add_country_field_country),
            placeholder = stringResource(R.string.add_country_placeholder_country),
            errorMessage = fieldErrorMessage(state.countryError),
            imeAction = ImeAction.Next
        )

        FormTextField(
            value = state.capital,
            onValueChange = onCapitalChange,
            label = stringResource(R.string.add_country_field_capital),
            placeholder = stringResource(R.string.add_country_placeholder_capital),
            errorMessage = fieldErrorMessage(state.capitalError),
            imeAction = ImeAction.Done
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(R.string.add_country_continent_label),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            ContinentDropdown(
                selected = state.continent,
                placeholder = stringResource(R.string.add_country_placeholder_continent),
                onSelected = onContinentSelected,
                errorMessage = if (state.continentMissing) {
                    stringResource(R.string.add_country_error_continent)
                } else {
                    null
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSaveClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = stringResource(R.string.add_country_save))
        }

        OutlinedButton(
            onClick = onBackClick,
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = stringResource(R.string.add_country_cancel))
        }

        state.lastAdded?.let { added ->
            FeedbackBanner(
                message = stringResource(
                    R.string.add_country_success,
                    added.name,
                    added.capital
                ),
                isCorrect = true
            )
        }

        if (state.addedCountries.isNotEmpty()) {
            Text(
                text = stringResource(R.string.add_country_session_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            state.addedCountries.forEach { country ->
                CountryListItem(
                    name = country.name,
                    detail = stringResource(
                        R.string.add_country_item_detail,
                        country.capital,
                        stringResource(country.continent.nameRes())
                    )
                )
            }
        }
      }
    }
}

@Composable
private fun fieldErrorMessage(error: FieldError?): String? = when (error) {
    null -> null
    FieldError.EMPTY -> stringResource(R.string.add_country_error_empty)
    FieldError.DUPLICATE -> stringResource(R.string.add_country_error_duplicate)
}