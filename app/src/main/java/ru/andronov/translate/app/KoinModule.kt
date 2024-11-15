package ru.andronov.translate.app

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.andronov.translate.app.ui.screen.TranslationViewModel

val appModule = module {
    viewModel { TranslationViewModel() }
}