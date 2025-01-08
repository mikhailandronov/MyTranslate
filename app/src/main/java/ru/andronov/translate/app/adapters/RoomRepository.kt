package ru.andronov.translate.app.adapters

import ru.andronov.translate.app.usecases.TranslationHistory
import ru.andronov.translate.app.usecases.TranslationHistoryRepository

class RoomRepository: TranslationHistoryRepository {
    override suspend fun insertHistory(historyRecord: TranslationHistory) {

    }

    override fun getTranslationHistory(): List<TranslationHistory>  =
        emptyList()
}