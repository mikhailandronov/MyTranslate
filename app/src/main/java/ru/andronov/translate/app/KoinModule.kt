package ru.andronov.translate.app

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.andronov.translate.app.adapters.AppDatabase
import ru.andronov.translate.app.adapters.FTApiTranslator
import ru.andronov.translate.app.adapters.MockRepository
import ru.andronov.translate.app.adapters.MockTranslator
import ru.andronov.translate.app.adapters.RoomRepository
import ru.andronov.translate.app.adapters.TranslationHistoryDao
import ru.andronov.translate.app.ui.screen.HistoryViewModel
import ru.andronov.translate.app.ui.screen.TranslationViewModel
import ru.andronov.translate.app.usecases.HistoryUseCase
import ru.andronov.translate.app.usecases.TranslateUseCase
import ru.andronov.translate.app.usecases.TranslationHistoryRepository
import ru.andronov.translate.app.usecases.TranslationService
import java.net.URL

val appModule = module {
    // **********************************************
    // Translation service implementations
    // **********************************************
    single<TranslationService>(named("FTAPI")) {
        FTApiTranslator(URL("https://ftapi.pythonanywhere.com/translate"))
    }

    single<TranslationService>(named("Mock")) {
        MockTranslator(URL("https://my.translator.com/translate"))
    }

    single { TranslateUseCase(get(qualifier = named("FTAPI"))) }

    // **********************************************
    // Translation history repository implementations
    // **********************************************
    single<AppDatabase> {
        AppDatabase.getDatabase(androidContext())
    }

    single<TranslationHistoryDao> {
        val db = get<AppDatabase>()
        db.getHistoryDao()
    }

    single<TranslationHistoryRepository>(named("Room")) {
        RoomRepository(get())
    }

    single<TranslationHistoryRepository>(named("Mock")) {
        MockRepository()
    }

    single { HistoryUseCase(get(qualifier = named("Room"))) }

    viewModel { TranslationViewModel(get(), get()) }
    viewModel { HistoryViewModel(get()) }
}