package com.circuit.journal.features.journal.layers.domain.usecase

import com.circuit.journal.features.journal.layers.domain.repo.JournalRepo

class GetJournalByIdUseCase(
    private val journalRepo: JournalRepo,
) {
    operator fun invoke(id: Long) = journalRepo.getJournalById(id)
}