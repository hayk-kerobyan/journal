package com.circuit.journal.features.journal.layers.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity("journals")
data class JournalDb(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") val id: Long,
    @ColumnInfo("text") val text: String,
    @ColumnInfo("savedAt") val savedAt: Date?
)
