package com.upchiapas.atlasquiz.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.upchiapas.atlasquiz.presentation.theme.AtlasEurope
import com.upchiapas.atlasquiz.presentation.theme.AtlasGold

@Composable
fun AnswerFeedback(
    title: String,
    message: String,
    isCorrect: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Surface(
            modifier = Modifier.size(64.dp),
            shape = CircleShape,
            color = if (isCorrect) {
                AtlasEurope.copy(alpha = 0.5f)
            } else {
                MaterialTheme.colorScheme.errorContainer
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isCorrect) Icons.Filled.Star else Icons.Filled.Close,
                    contentDescription = null,
                    tint = if (isCorrect) AtlasGold else MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(text = message, style = MaterialTheme.typography.bodyMedium)
        }
    }
}