package com.circuit.journal

import android.app.Application
import com.circuit.journal.di.appModule
import com.circuit.journal.features.journal.di.journalModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(appModule, journalModule)
        }
    }
}