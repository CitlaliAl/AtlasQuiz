package com.upchiapas.atlasquiz.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.upchiapas.atlasquiz.R
import com.upchiapas.atlasquiz.model.Continent

@Composable
fun ContinentSelector(
    selected: Continent?,
    onSelected: (Continent) -> Unit,
    errorMessage: String?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ContinentChip(
                label = stringResource(R.string.continent_asia),
                selected = selected == Continent.ASIA,
                onClick = { onSelected(Continent.ASIA) }
            )
            ContinentChip(
                label = stringResource(R.string.continent_europe),
                selected = selected == Continent.EUROPE,
                onClick = { onSelected(Continent.EUROPE) }
            )
        }
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun ContinentChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(text = label) },
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}