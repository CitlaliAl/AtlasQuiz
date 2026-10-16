package com.upchiapas.atlasquiz.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.upchiapas.atlasquiz.R

enum class AnswerOptionStatus {
    IDLE,
    CORRECT,
    WRONG,
    DISABLED
}

@Composable
fun AnswerOptionButton(
    text: String,
    status: AnswerOptionStatus,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = when (status) {
        AnswerOptionStatus.CORRECT -> MaterialTheme.colorScheme.primaryContainer
        AnswerOptionStatus.WRONG -> MaterialTheme.colorScheme.errorContainer
        else -> MaterialTheme.colorScheme.surface
    }
    val borderColor = when (status) {
        AnswerOptionStatus.CORRECT -> MaterialTheme.colorScheme.primary
        AnswerOptionStatus.WRONG -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.outline
    }
    val contentColor = MaterialTheme.colorScheme.onSurface

    Card(
        onClick = onClick,
        enabled = status == AnswerOptionStatus.IDLE,
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (status == AnswerOptionStatus.DISABLED) 0.6f else 1f),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor,
            disabledContentColor = contentColor
        ),
        border = BorderStroke(2.dp, borderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            when (status) {
                AnswerOptionStatus.CORRECT -> Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = stringResource(R.string.quiz_option_correct),
                    tint = MaterialTheme.colorScheme.primary
                )
                AnswerOptionStatus.WRONG -> Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(R.string.quiz_option_wrong),
                    tint = MaterialTheme.colorScheme.error
                )
                else -> Unit
            }
        }
    }
}