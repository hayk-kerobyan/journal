package com.circuit.journal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.circuit.journal.features.journal.layers.presenter.JournalEvent
import com.circuit.journal.features.journal.layers.presenter.JournalScreen
import com.circuit.journal.features.journal.layers.presenter.JournalViewModel
import com.circuit.journal.features.journal.layers.presenter.OnJournalSelected
import com.circuit.journal.features.journal.layers.presenter.OnJournalTextChange
import com.circuit.journal.features.journal.layers.presenter.OnSaveClicked
import org.koin.androidx.compose.koinViewModel


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
            val viewModel = koinViewModel<JournalViewModel>()
            val state by viewModel.state.collectAsState()
            JournalScreen(
                state = state,
                onValueChange = { viewModel.onEvent(OnJournalTextChange(it)) },
                onSaveClick = { viewModel.onEvent(OnSaveClicked(it)) },
                onShareClick = {},
                onJournalSelected = { viewModel.onEvent(OnJournalSelected(it)) }
            )
        }
    }
}

