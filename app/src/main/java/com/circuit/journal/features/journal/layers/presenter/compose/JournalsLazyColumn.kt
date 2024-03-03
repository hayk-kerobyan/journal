package com.circuit.journal.features.journal.layers.presenter.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.circuit.journal.features.journal.layers.domain.model.Journal
import kotlinx.coroutines.flow.Flow

@Composable
fun JournalsLazyColumn(
    modifier: Modifier,
    journalPagingItems: Flow<PagingData<Journal>>,
    onJournalSelected: (Journal) -> Unit,
) {
    val pagingItems = journalPagingItems.collectAsLazyPagingItems()
    LazyColumn(
        modifier = modifier
    ) {
        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey { it.id },
            contentType = pagingItems.itemContentType { "Journals" },
        ) { index ->
            pagingItems[index]?.let { journal ->
                JournalListItem(
                    journal = journal,
                    onClick = onJournalSelected
                )
            }
        }
    }
}