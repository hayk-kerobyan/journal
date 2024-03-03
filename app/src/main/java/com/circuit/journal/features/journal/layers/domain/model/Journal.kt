package com.circuit.journal.features.journal.layers.domain.model

import java.util.Date

data class Journal(
    val id: Long,
    val savedAt: Date?,
    val text: String
)