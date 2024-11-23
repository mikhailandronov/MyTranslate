package ru.andronov.translate.app

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.andronov.translate.app.adapters.FTApiTranslator
import ru.andronov.translate.app.adapters.MockTranslator
import ru.andronov.translate.app.ui.screen.TranslationViewModel
import ru.andronov.translate.app.usecases.TranslateUseCase
import ru.andronov.translate.app.usecases.TranslationService
import java.net.URL
import kotlin.math.sin

val appModule = module {
    single<TranslationService>(named("FTAPI")) {
        FTApiTranslator(URL("https://ftapi.pythonanywhere.com/translate"))
    }

    single<TranslationService>(named("Mock")) {
        MockTranslator(URL("https://my.translator.com/translate"))
    }

    single { TranslateUseCase(get(qualifier = named("FTAPI"))) }

    viewModel { TranslationViewModel(get()) }
}