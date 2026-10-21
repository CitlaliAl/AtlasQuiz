package com.upchiapas.atlasquiz.presentation.mapper

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.upchiapas.atlasquiz.R
import com.upchiapas.atlasquiz.model.Continent
import com.upchiapas.atlasquiz.presentation.theme.AtlasAsia
import com.upchiapas.atlasquiz.presentation.theme.AtlasEurope

@StringRes
fun Continent.nameRes(): Int = when (this) {
    Continent.ASIA -> R.string.continent_asia
    Continent.EUROPE -> R.string.continent_europe
}

@DrawableRes
fun Continent.imageRes(): Int = when (this) {
    Continent.ASIA -> R.drawable.continent_asia
    Continent.EUROPE -> R.drawable.continent_europe
}

fun Continent.cardColor(): Color = when (this) {
    Continent.ASIA -> AtlasAsia
    Continent.EUROPE -> AtlasEurope
}