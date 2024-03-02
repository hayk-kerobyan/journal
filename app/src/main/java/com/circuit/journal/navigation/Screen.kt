package com.circuit.journal.navigation

sealed class Screen(val route: String) {
    data object JournalScreen : Screen("journal_screen")
}