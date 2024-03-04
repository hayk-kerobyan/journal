package com.circuit.journal.features.journal.layers.presenter.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.circuit.journal.common.extensions.displayedDateAndTime
import com.circuit.journal.features.journal.layers.domain.model.Journal

@Composable
fun JournalListItem(
    journal: Journal,
    onClick: (Journal) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(journal) }
            .padding(16.dp),
    ) {
        Text(text = journal.text.take(20), maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = journal.savedAt?.displayedDateAndTime() ?: "")
    }
}