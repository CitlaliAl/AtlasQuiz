package com.upchiapas.atlasquiz.presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val AtlasColorScheme = lightColorScheme(
    primary = AtlasGreen,
    onPrimary = Color.White,
    primaryContainer = AtlasGreenSoft,
    onPrimaryContainer = AtlasNavy,
    secondary = AtlasNavy,
    onSecondary = Color.White,
    background = AtlasBackground,
    onBackground = AtlasNavy,
    surface = AtlasSurface,
    onSurface = AtlasNavy,
    outline = AtlasOutline,
    error = AtlasError,
    errorContainer = AtlasErrorSoft,
    onErrorContainer = AtlasError
)

private val AtlasShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp)
)

@Composable
fun AtlasQuizTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AtlasColorScheme,
        shapes = AtlasShapes,
        content = content
    )
}