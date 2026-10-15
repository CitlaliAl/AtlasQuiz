package com.upchiapas.atlasquiz.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.upchiapas.atlasquiz.presentation.home.HomeScreen
import com.upchiapas.atlasquiz.presentation.navigation.AtlasQuizRoutes

@Composable
fun AtlasQuizApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AtlasQuizRoutes.HOME,
        modifier = Modifier.safeDrawingPadding()
    ) {
        composable(AtlasQuizRoutes.HOME) {
            HomeScreen(
                onStartQuiz = { navController.navigate(AtlasQuizRoutes.QUIZ) },
                onAddCountry = { navController.navigate(AtlasQuizRoutes.ADD_COUNTRY) }
            )
        }
        composable(AtlasQuizRoutes.QUIZ) { PlaceholderScreen(AtlasQuizRoutes.QUIZ) }
        composable(AtlasQuizRoutes.RESULT) { PlaceholderScreen(AtlasQuizRoutes.RESULT) }
        composable(AtlasQuizRoutes.ADD_COUNTRY) { PlaceholderScreen(AtlasQuizRoutes.ADD_COUNTRY) }
    }
}

// Temporal: reemplazado pantalla por pantalla en los próximos commits
@Composable
private fun PlaceholderScreen(name: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = name, style = MaterialTheme.typography.headlineMedium)
    }
}