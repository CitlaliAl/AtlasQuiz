package com.upchiapas.atlasquiz.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upchiapas.atlasquiz.R
import com.upchiapas.atlasquiz.model.Continent
import com.upchiapas.atlasquiz.model.QuizRules
import com.upchiapas.atlasquiz.presentation.components.ContinentCard
import com.upchiapas.atlasquiz.presentation.components.GreetingHeader
import com.upchiapas.atlasquiz.presentation.components.ProgressSummaryCard
import com.upchiapas.atlasquiz.presentation.theme.AtlasAsia
import com.upchiapas.atlasquiz.presentation.theme.AtlasEurope

@Composable
fun HomeScreen(
    onStartQuiz: (Continent) -> Unit,
    onAddCountry: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HomeContent(
        state = state,
        onContinentSelected = viewModel::onContinentSelected,
        onStartClick = { state.selectedContinent?.let(onStartQuiz) },
        onAddCountryClick = onAddCountry
    )
}

@Composable
fun HomeContent(
    state: HomeUiState,
    onContinentSelected: (Continent) -> Unit,
    onStartClick: () -> Unit,
    onAddCountryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sidePadding = Modifier.padding(horizontal = 20.dp)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        GreetingHeader(
            title = stringResource(R.string.home_greeting),
            subtitle = stringResource(R.string.home_greeting_subtitle),
            modifier = sidePadding
        )

        Text(
            text = stringResource(R.string.home_question),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = sidePadding
        )

        ProgressSummaryCard(
            label = stringResource(R.string.home_progress_label),
            summary = stringResource(
                R.string.home_progress_summary,
                state.quizzesCompleted,
                state.totalPoints
            ),
            note = stringResource(R.string.home_progress_note),
            modifier = sidePadding
        )

        Text(
            text = stringResource(R.string.home_featured_label).uppercase(),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            modifier = sidePadding
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                ContinentCard(
                    title = stringResource(R.string.home_card_title_asia),
                    subtitle = stringResource(
                        R.string.home_card_subtitle,
                        state.asiaCount,
                        QuizRules.QUESTIONS_PER_QUIZ
                    ),
                    imageRes = R.drawable.continent_asia,
                    color = AtlasAsia,
                    selected = state.selectedContinent == Continent.ASIA,
                    onClick = { onContinentSelected(Continent.ASIA) },
                    modifier = Modifier.width(220.dp)
                )
            }
            item {
                ContinentCard(
                    title = stringResource(R.string.home_card_title_europe),
                    subtitle = stringResource(
                        R.string.home_card_subtitle,
                        state.europeCount,
                        QuizRules.QUESTIONS_PER_QUIZ
                    ),
                    imageRes = R.drawable.continent_europe,
                    color = AtlasEurope,
                    selected = state.selectedContinent == Continent.EUROPE,
                    onClick = { onContinentSelected(Continent.EUROPE) },
                    modifier = Modifier.width(220.dp)
                )
            }
        }

        if (state.selectedContinent == null) {
            Text(
                text = stringResource(R.string.home_choose_continent),
                style = MaterialTheme.typography.bodyMedium,
                modifier = sidePadding
            )
        }

        if (state.needsMoreCountries) {
            Text(
                text = stringResource(
                    R.string.home_need_more_countries,
                    QuizRules.QUESTIONS_PER_QUIZ
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = sidePadding
            )
        }

        Button(
            onClick = onStartClick,
            enabled = state.canStart,
            modifier = sidePadding
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = stringResource(R.string.home_start_quiz))
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null
            )
        }

        OutlinedButton(
            onClick = onAddCountryClick,
            modifier = sidePadding
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(R.string.home_add_country))
        }
    }
}