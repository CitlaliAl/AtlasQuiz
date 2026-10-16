package com.upchiapas.atlasquiz.presentation

import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
    val homeViewModel: HomeViewModel = viewModel()
    val quizViewModel: QuizViewModel = viewModel()

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
                onQuizFinished = { score ->
                    homeViewModel.onQuizCompleted(score)
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
            AddCountryScreen(onBack = { navController.popBackStack() })
        }
    }
}