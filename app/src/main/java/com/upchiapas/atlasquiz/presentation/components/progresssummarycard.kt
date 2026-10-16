package com.upchiapas.atlasquiz.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.upchiapas.atlasquiz.presentation.theme.AtlasGreenDark

@Composable
fun ProgressSummaryCard(
    label: String,
    summary: String,
    note: String,
    modifier: Modifier = Modifier
) {
    val softContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.75f)

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = AtlasGreenDark,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = label.uppercase(),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = softContentColor
            )
            Text(
                text = summary,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = note,
                style = MaterialTheme.typography.bodySmall,
                color = softContentColor
            )
        }
    }
}