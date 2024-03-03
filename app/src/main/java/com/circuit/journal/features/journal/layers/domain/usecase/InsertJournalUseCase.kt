package com.circuit.journal.features.journal.layers.domain.usecase

import com.circuit.journal.features.journal.layers.domain.model.Journal
import com.circuit.journal.features.journal.layers.domain.repo.JournalRepo
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class InsertJournalUseCase(
    private val context: CoroutineContext,
    private val journalRepo: JournalRepo,
) {
    operator fun invoke(journal: Journal) = flow {
            emit(journalRepo.insertJournal(journal))
    }.flowOn(context)
}