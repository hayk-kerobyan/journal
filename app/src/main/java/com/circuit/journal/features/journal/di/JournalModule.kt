package com.circuit.journal.features.journal.di

import com.circuit.journal.db.AppDatabase
import com.circuit.journal.features.journal.layers.data.datasource.local.JournalDao
import org.koin.dsl.module

val journalModule = module {

    single<JournalDao> {
        get<AppDatabase>().journalDao()
    }

}