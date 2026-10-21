package com.upchiapas.atlasquiz.presentation.quiz

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.upchiapas.atlasquiz.R
import com.upchiapas.atlasquiz.model.Continent
import com.upchiapas.atlasquiz.model.QuizRules
import com.upchiapas.atlasquiz.presentation.components.AnswerOptionButton
import com.upchiapas.atlasquiz.presentation.components.AnswerOptionStatus
import com.upchiapas.atlasquiz.presentation.components.FeedbackBanner
import com.upchiapas.atlasquiz.presentation.components.QuestionCard
import com.upchiapas.atlasquiz.presentation.components.QuizProgressHeader
import com.upchiapas.atlasquiz.presentation.components.ScreenHeader
import com.upchiapas.atlasquiz.presentation.theme.AtlasAsia
import com.upchiapas.atlasquiz.presentation.theme.AtlasEurope

@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    onBack: () -> Unit,
    onQuizFinished: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.isFinished) {
        if (state.isFinished) onQuizFinished()
    }

    QuizContent(
        state = state,
        onAnswerSelected = viewModel::onAnswerSelected,
        onContinueClick = viewModel::onContinue,
        onBackClick = onBack
    )
}

@Composable
fun QuizContent( //Sin estado: solo dibuja y reporta toques
    state: QuizUiState,
    onAnswerSelected: (String) -> Unit,
    onContinueClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val question = state.currentQuestion ?: return
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ScreenHeader(
                title = stringResource(
                    R.string.quiz_top_title,
                    stringResource(if (isEurope) R.string.continent_europe else R.string.continent_asia)
                ),
                onBackClick = onBackClick
            )

            QuizProgressHeader(
                progressLabel = stringResource(
                    R.string.quiz_progress_label,
                    state.currentIndex + 1,
                    state.questions.size
                ),
                pointsLabel = stringResource(R.string.quiz_points_label, state.score),
                progress = state.progress
            )

        QuestionCard(
            label = stringResource(
                if (isEurope) R.string.continent_europe else R.string.continent_asia
            ),
            question = stringResource(R.string.quiz_question, question.country.name),
            color = if (isEurope) AtlasEurope else AtlasAsia
        )

        question.options.forEach { option ->
            AnswerOptionButton(
                text = option,
                status = optionStatus(option, question, state),
                onClick = { onAnswerSelected(option) }
            )
        }

        if (state.isAnswered) {
            FeedbackBanner(
                message = if (state.isAnswerCorrect) {
                    stringResource(
                        R.string.quiz_feedback_correct,
                        QuizRules.POINTS_PER_CORRECT
                    )
                } else {
                    stringResource(
                        R.string.quiz_feedback_wrong,
                        question.country.name,
                        question.country.capital
                    )
                },
                isCorrect = state.isAnswerCorrect
            )
        }

        Button(
            onClick = onContinueClick,
            enabled = state.isAnswered,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = stringResource(
                    if (state.isLastQuestion) R.string.quiz_see_results else R.string.quiz_continue
                )
            )
        }
      }
    }
}



private fun optionStatus(
    option: String,
    question: QuizQuestion,
    state: QuizUiState
): AnswerOptionStatus = when {
    !state.isAnswered -> AnswerOptionStatus.IDLE
    option == question.country.capital -> AnswerOptionStatus.CORRECT
    option == state.selectedCapital -> AnswerOptionStatus.WRONG
    else -> AnswerOptionStatus.DISABLED
}