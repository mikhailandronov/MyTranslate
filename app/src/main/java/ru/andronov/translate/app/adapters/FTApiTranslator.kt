package ru.andronov.translate.app.adapters

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.andronov.translate.app.usecases.LanguageCode
import ru.andronov.translate.app.usecases.TranslationService
import java.net.URL


class FTApiTranslator(val endpoint: URL)    : TranslationService {
    override suspend fun translate(
        sourceText: String,
        sourceLang: LanguageCode,
        targetLang: LanguageCode
    ): String {
        return "Переведенная строка с сервиса ${endpoint.host}.\n" +
                "Перевод c ${sourceLang.code} на ${targetLang.code}"
    }
}
