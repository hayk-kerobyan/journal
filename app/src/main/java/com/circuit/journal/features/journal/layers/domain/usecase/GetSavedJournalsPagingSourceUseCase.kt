package com.circuit.journal.features.journal.layers.domain.usecase

import com.circuit.journal.features.journal.layers.domain.repo.JournalRepo

class GetSavedJournalsPagingSourceUseCase(
    private val journalRepo: JournalRepo,
) {
    operator fun invoke() = journalRepo.getSavedJournalsPagingSource()
}