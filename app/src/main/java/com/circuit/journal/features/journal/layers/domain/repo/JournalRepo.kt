package com.circuit.journal.features.journal.layers.domain.repo

import androidx.paging.PagingData
import com.circuit.journal.features.journal.layers.domain.model.Journal
import kotlinx.coroutines.flow.Flow

interface JournalRepo {
    suspend fun insertJournal(journal: Journal)
    fun getUnsavedJournal(): Flow<Journal?>
    fun getJournalById(id:Long): Flow<Journal>
    fun getSavedJournalsPagingSource(): Flow<PagingData<Journal>>
}