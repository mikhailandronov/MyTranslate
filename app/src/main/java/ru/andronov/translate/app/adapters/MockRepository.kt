package ru.andronov.translate.app.adapters

import ru.andronov.translate.app.usecases.TranslationHistory
import ru.andronov.translate.app.usecases.TranslationHistoryRepository

class MockRepository: TranslationHistoryRepository {
    override suspend fun insertHistory(historyRecord: TranslationHistory) {

    }

    override fun getTranslationHistory(): List<TranslationHistory>  =
        listOf(
            TranslationHistory(
                id = 0,
                sourceText = "Hello, how are you?",
                translatedText = "Привет, как дела?"
            ),
            TranslationHistory(
                id = 1,
                sourceText = "Здравствуйте! Подскажите, который час?",
                translatedText = "Hello! Can you please tell me what time it is now?"
            ),
        )
}