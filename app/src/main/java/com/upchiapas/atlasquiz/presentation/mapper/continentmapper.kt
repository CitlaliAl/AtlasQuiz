package com.upchiapas.atlasquiz.presentation.mapper

import androidx.annotation.StringRes
import com.upchiapas.atlasquiz.R
import com.upchiapas.atlasquiz.model.Continent

@StringRes
fun Continent.nameRes(): Int = when (this) {
    Continent.ASIA -> R.string.continent_asia
    Continent.EUROPE -> R.string.continent_europe
}