package com.circuit.journal.features.journal.layers.data.datasource.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.circuit.journal.features.journal.layers.data.model.JournalDb
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(journal: JournalDb): Long

    @Query("SELECT * FROM journals WHERE savedAt IS NULL")
    fun getUnsavedJournal(): Flow<JournalDb>

    @Query("SELECT * FROM journals WHERE id = :id")
    fun getById(id: Long): Flow<JournalDb>

    @Query("SELECT * FROM journals WHERE savedAt IS NOT NULL")
    fun getSavedJournalsPagingSource(): PagingSource<Int, JournalDb>
}