package com.circuit.journal.features.journal.layers.domain.model

import com.circuit.journal.features.journal.layers.data.model.JournalDb

fun Journal.toDb() = JournalDb(
    id = id,
    savedAt = savedAt,
    text = text,
)

fun JournalDb.toDomain() = Journal(
    id = id,
    savedAt = savedAt,
    text = text,
)

