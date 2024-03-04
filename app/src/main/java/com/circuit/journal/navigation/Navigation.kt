package com.circuit.journal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.circuit.journal.features.journal.layers.presenter.JournalScreen
import com.circuit.journal.features.journal.layers.presenter.JournalViewModel
import com.circuit.journal.features.journal.layers.presenter.OnHtmlSendError
import com.circuit.journal.features.journal.layers.presenter.OnJournalTextChange
import com.circuit.journal.features.journal.layers.presenter.OnSaveClicked
import com.circuit.journal.features.journal.layers.presenter.OnShareClicked
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


const val KEY_JOURNAL_ID = "id"

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.JournalScreen.route) {
        composable(
            route = Screen.JournalScreen.route + "?$KEY_JOURNAL_ID={id}",
            arguments = listOf(
                navArgument(KEY_JOURNAL_ID) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            val id = it.arguments?.getString(KEY_JOURNAL_ID)?.toLong()
            val viewModel = koinViewModel<JournalViewModel> { parametersOf(id) }
            val state by viewModel.state.collectAsStateWithLifecycle()
            JournalScreen(
                state = state,
                actions = viewModel.action,
                onValueChange = { viewModel.onEvent(OnJournalTextChange(it)) },
                onSaveClick = { viewModel.onEvent(OnSaveClicked(it)) },
                onShareClick = { journal, transformationConfigs ->
                    viewModel.onEvent(OnShareClicked(journal, transformationConfigs))
                },
                onJournalSelected = {
                    navController.navigate(
                        Screen.JournalScreen.route + "?$KEY_JOURNAL_ID=${it.id}"
                    )
                },
                onHtmlSendError = { viewModel.onEvent(OnHtmlSendError) },
            )
        }
    }
}

