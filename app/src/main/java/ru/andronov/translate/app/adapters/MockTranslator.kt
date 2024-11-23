package ru.andronov.translate.app.adapters

import ru.andronov.translate.app.usecases.LanguageCode
import ru.andronov.translate.app.usecases.TranslationService
import java.net.URL


class MockTranslator(val endpoint: URL)    : TranslationService {
    override suspend fun translate(
        sourceText: String,
        sourceLang: LanguageCode,
        targetLang: LanguageCode
    ): String {
        return "Переведенная строка с сервиса ${endpoint.host}.\n" +
                "Перевод c ${sourceLang.code} на ${targetLang.code}"
    }
}