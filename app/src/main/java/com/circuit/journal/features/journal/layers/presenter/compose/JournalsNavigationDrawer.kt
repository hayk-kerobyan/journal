package com.circuit.journal.features.journal.layers.presenter.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import com.circuit.journal.features.journal.layers.domain.model.Journal
import kotlinx.coroutines.flow.Flow

@Composable
fun JournalNavigationDrawer(
    modifier: Modifier,
    drawerState: DrawerState,
    journalPagingItems: Flow<PagingData<Journal>>,
    onJournalSelected: (Journal) -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            ModalDrawerSheet {
                Text("Saved Journals", modifier = Modifier.padding(16.dp))
                Divider()
                JournalsLazyColumn(
                    Modifier.fillMaxSize(),
                    journalPagingItems,
                    onJournalSelected
                )
            }
        },
        content = content
    )
}

