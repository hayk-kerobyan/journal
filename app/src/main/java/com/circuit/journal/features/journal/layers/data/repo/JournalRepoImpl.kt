package com.circuit.journal.features.journal.layers.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.circuit.journal.db.AppDatabase
import com.circuit.journal.features.journal.layers.data.datasource.local.JournalDao
import com.circuit.journal.features.journal.layers.domain.model.Journal
import com.circuit.journal.features.journal.layers.domain.model.toDb
import com.circuit.journal.features.journal.layers.domain.model.toDomain
import com.circuit.journal.features.journal.layers.domain.repo.JournalRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class JournalRepoImpl(
    private val journalDao: JournalDao,
) : JournalRepo {

    override suspend fun insertJournal(journal: Journal) {
        journalDao.insert(journal.toDb())
    }

    override fun getUnsavedJournal(): Flow<Journal?> {
        return journalDao.getUnsavedJournal().map { it?.toDomain() }
    }

    override fun getJournalById(id: Long): Flow<Journal> {
        return journalDao.getById(id).map { it.toDomain() }
    }

    override fun getSavedJournalsPagingSource(): Flow<PagingData<Journal>> {
        return Pager(
            config = PagingConfig(pageSize = 20, initialLoadSize = 20),
            pagingSourceFactory = { journalDao.getSavedJournalsPagingSource() }
        ).flow
            .map {
                it.map { it.toDomain() }
            }
    }
}