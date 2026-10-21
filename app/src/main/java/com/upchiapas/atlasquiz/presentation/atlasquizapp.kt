package com.upchiapas.atlasquiz.presentation

import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.upchiapas.atlasquiz.AtlasQuizApplication
import com.upchiapas.atlasquiz.presentation.addcountry.AddCountryScreen
import com.upchiapas.atlasquiz.presentation.home.HomeScreen
import com.upchiapas.atlasquiz.presentation.home.HomeViewModel
import com.upchiapas.atlasquiz.presentation.navigation.AtlasQuizRoutes
import com.upchiapas.atlasquiz.presentation.quiz.QuizScreen
import com.upchiapas.atlasquiz.presentation.quiz.QuizViewModel
import com.upchiapas.atlasquiz.presentation.result.ResultScreen

@Composable
fun AtlasQuizApp() {
    val navController = rememberNavController()
    val application = LocalContext.current.applicationContext as AtlasQuizApplication
    val factory = application.module.viewModelFactory
    val homeViewModel: HomeViewModel = viewModel(factory = factory)
    val quizViewModel: QuizViewModel = viewModel(factory = factory)

    NavHost(
        navController = navController,
        startDestination = AtlasQuizRoutes.HOME,
        modifier = Modifier.safeDrawingPadding()
    ) {
        composable(AtlasQuizRoutes.HOME) {
            HomeScreen(
                viewModel = homeViewModel,
                onStartQuiz = { continent ->
                    quizViewModel.startQuiz(continent)
                    navController.navigate(AtlasQuizRoutes.QUIZ)
                },
                onAddCountry = { navController.navigate(AtlasQuizRoutes.ADD_COUNTRY) }
            )
        }
        composable(AtlasQuizRoutes.QUIZ) {
            QuizScreen(
                viewModel = quizViewModel,
                onBack = { navController.popBackStack() },
                onQuizFinished = {
                    navController.navigate(AtlasQuizRoutes.RESULT) {
                        popUpTo(AtlasQuizRoutes.QUIZ) { inclusive = true }
                    }
                }
            )
        }
        composable(AtlasQuizRoutes.RESULT) {
            val quizState by quizViewModel.uiState.collectAsStateWithLifecycle()
            ResultScreen(
                state = quizState,
                onPlayAgain = {
                    quizViewModel.restartQuiz()
                    navController.navigate(AtlasQuizRoutes.QUIZ) {
                        popUpTo(AtlasQuizRoutes.RESULT) { inclusive = true }
                    }
                },
                onGoHome = {
                    navController.popBackStack(AtlasQuizRoutes.HOME, inclusive = false)
                }
            )
        }
        composable(AtlasQuizRoutes.ADD_COUNTRY) {
            AddCountryScreen(
                viewModel = viewModel(factory = factory),
                onBack = { navController.popBackStack() }
            )
        }
    }
}