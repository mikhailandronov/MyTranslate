package ru.andronov.translate.app

import android.app.Application
import org.koin.core.context.startKoin

class TranslatorApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            modules(appModule)
        }
    }
}