package com.circuit.journal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.circuit.journal.features.journal.layers.presenter.JournalScreen


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
            JournalScreen()
        }
    }
}

