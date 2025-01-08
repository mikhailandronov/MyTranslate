package ru.andronov.translate.app.usecases

import kotlinx.coroutines.flow.flowOf
import java.util.Date

data class TranslationHistory(
    val id: Long = 0,
    val sourceText: String,
    val translatedText: String,
    val timestamp: Long = Date().time,
)

interface TranslationHistoryRepository {
    suspend fun insertHistory(historyRecord: TranslationHistory)
    fun getTranslationHistory(): List<TranslationHistory>
}

class HistoryUseCase(
    private val repository: TranslationHistoryRepository
) {
    suspend fun save(sourceText: String, translatedText: String) {
        repository.insertHistory(
            TranslationHistory(
                sourceText = sourceText,
                translatedText = translatedText
            )
        )
    }

    fun getHistory() = flowOf(repository.getTranslationHistory())
}

