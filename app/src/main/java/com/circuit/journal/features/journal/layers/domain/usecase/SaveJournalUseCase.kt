package com.circuit.journal.features.journal.layers.domain.usecase

import com.circuit.journal.features.journal.layers.domain.model.Journal
import com.circuit.journal.features.journal.layers.domain.repo.JournalRepo
import com.circuit.journal.features.journal.utils.exceptions.JournalBlankTextException
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.lang.RuntimeException
import java.util.Date
import kotlin.coroutines.CoroutineContext

class SaveJournalUseCase(
    private val context: CoroutineContext,
    private val journalRepo: JournalRepo,
) {
    operator fun invoke(journal: Journal) = flow {
        if(journal.text.isBlank()) {
            throw JournalBlankTextException()
        }
        emit(journalRepo.insertJournal(journal.copy(savedAt = Date())))
    }.flowOn(context)
}

