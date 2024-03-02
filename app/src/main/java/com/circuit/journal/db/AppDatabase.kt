package com.circuit.journal.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.circuit.journal.db.converters.DateConverter
import com.circuit.journal.features.journal.layers.data.datasource.local.JournalDao
import com.circuit.journal.features.journal.layers.data.model.JournalDb

@Database(
    entities = [JournalDb::class],
    version = 1
)
@TypeConverters(value = [DateConverter::class])
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "app_db"
    }
    abstract fun journalDao(): JournalDao
}