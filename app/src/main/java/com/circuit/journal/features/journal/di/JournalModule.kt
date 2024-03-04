package com.circuit.journal.features.journal.di

import com.circuit.journal.db.AppDatabase
import com.circuit.journal.features.journal.layers.data.datasource.local.JournalDao
import com.circuit.journal.features.journal.layers.data.repo.JournalRepoImpl
import com.circuit.journal.features.journal.layers.domain.repo.JournalRepo
import com.circuit.journal.features.journal.layers.domain.usecase.ConvertToHtmlUseCase
import com.circuit.journal.features.journal.layers.domain.usecase.GetJournalByIdUseCase
import com.circuit.journal.features.journal.layers.domain.usecase.GetSavedJournalsPagingSourceUseCase
import com.circuit.journal.features.journal.layers.domain.usecase.GetUnsavedJournalUseCase
import com.circuit.journal.features.journal.layers.domain.usecase.InsertJournalUseCase
import com.circuit.journal.features.journal.layers.domain.usecase.SaveJournalUseCase
import com.circuit.journal.features.journal.layers.presenter.JournalViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val journalModule = module {

    single<JournalDao> {
        get<AppDatabase>().journalDao()
    }

    single<JournalRepo> {
        JournalRepoImpl(get())
    }

    single {
        InsertJournalUseCase(get(named("io")), get())
    }

    single {
        SaveJournalUseCase(get(named("io")), get())
    }

    single {
        GetUnsavedJournalUseCase(get())
    }

    single {
        GetJournalByIdUseCase(get())
    }

    single {
        GetSavedJournalsPagingSourceUseCase(get())
    }

    single {
        ConvertToHtmlUseCase(get(named("io")))
    }

    viewModel { parameters ->
        JournalViewModel(parameters.getOrNull(), get(), get(), get(), get(), get(), get())
    }
}