package com.upchiapas.atlasquiz.presentation.result

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.upchiapas.atlasquiz.R
import com.upchiapas.atlasquiz.model.Continent
import com.upchiapas.atlasquiz.presentation.components.AnswerReviewItem
import com.upchiapas.atlasquiz.presentation.components.ScoreSummaryCard
import com.upchiapas.atlasquiz.presentation.components.ScreenHeader
import com.upchiapas.atlasquiz.presentation.quiz.QuizUiState
import com.upchiapas.atlasquiz.presentation.quiz.ResultLevel

@Composable
// Es un solo composable sin estado propio. Solo dibuja lo que recibe, y avisa con callbacks
fun ResultScreen(
    state: QuizUiState,
    onPlayAgain: () -> Unit,
    onGoHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isEurope = state.continent == Continent.EUROPE

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
            title = stringResource(R.string.result_title),
            onBackClick = onGoHome
        )

        ScoreSummaryCard(
            title = stringResource(messageFor(state.resultLevel)),
            summary = stringResource(
                R.string.result_correct_summary,
                state.correctCount,
                state.questions.size,
                state.percentage
            ),
            points = stringResource(R.string.result_points, state.score),
            continentName = stringResource(
                if (isEurope) R.string.continent_europe else R.string.continent_asia
            )
        )

        Text(
            text = stringResource(R.string.result_review_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        state.answers.forEach { answer ->
            AnswerReviewItem(
                countryName = answer.countryName,
                detail = if (answer.isCorrect) {
                    answer.correctCapital
                } else {
                    stringResource(
                        R.string.result_wrong_detail,
                        answer.selectedCapital,
                        answer.correctCapital
                    )
                },
                isCorrect = answer.isCorrect
            )
        }

        Button(
            onClick = onPlayAgain,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = stringResource(R.string.result_play_again))
        }

        OutlinedButton(
            onClick = onGoHome,
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = stringResource(R.string.result_go_home))
        }
      }
    }
}

@StringRes
private fun messageFor(level: ResultLevel): Int = when (level) {
    ResultLevel.EXCELLENT -> R.string.result_message_excellent
    ResultLevel.GOOD -> R.string.result_message_good
    ResultLevel.KEEP_PRACTICING -> R.string.result_message_practice
}