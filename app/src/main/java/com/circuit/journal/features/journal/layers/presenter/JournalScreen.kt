package com.circuit.journal.features.journal.layers.presenter

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.circuit.journal.common.theme.JournalTheme
import com.circuit.journal.composables.journal.Journal
import com.circuit.journal.composables.journal.defaults.BoldTransformationConfig
import com.circuit.journal.composables.journal.defaults.HeadlineTransformationConfig
import com.circuit.journal.features.journal.layers.domain.model.Journal
import com.circuit.journal.features.journal.layers.presenter.compose.JournalNavigationDrawer
import com.circuit.journal.features.journal.layers.presenter.compose.JournalTopBar
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@Composable
fun JournalScreen(
    state: JournalState,
    onValueChange: (String) -> Unit,
    onSaveClick: (Journal) -> Unit,
    onShareClick: () -> Unit,
    onJournalSelected: (Journal) -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    state.journal ?: return
//    var text by remember { mutableStateOf(state.journal.text) }

    Scaffold(
        topBar = {
            JournalTopBar(
                onMenuClick = {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                },
                onSaveClick = {
                    onSaveClick(state.journal)
                },
                onShareClick = {
                    onShareClick()
                },
            )
        },
    ) {
        JournalNavigationDrawer(
            modifier = Modifier.padding(top = it.calculateTopPadding()),
            drawerState = drawerState,
            journalPagingItems = state.journals,
            onJournalSelected = onJournalSelected
        ) {


            Journal(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                value = state.journal.text,
                onValueChange = {
                    onValueChange(it)
                },
                transformationConfigs = listOf(
                    HeadlineTransformationConfig,
                    BoldTransformationConfig
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JournalScreenPreview() {
    JournalTheme {
        JournalScreen(
            state = JournalState(
                journal = com.circuit.journal.features.journal.layers.domain.model.Journal(
                    id = 0L,
                    savedAt = null,
                    text = ">This is Headline\n"
                ),
                isLoading = false,
                journals = flow { }
            ),
            onValueChange = {},
            onSaveClick = {},
            onShareClick = {},
            onJournalSelected = {}
        )
    }
}