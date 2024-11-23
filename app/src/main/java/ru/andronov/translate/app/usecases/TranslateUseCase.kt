package ru.andronov.translate.app.usecases

enum class LanguageCode(val code: String) {
    AUTO_DETECT(""),
    ENGLISH("en"),
    RUSSIAN("ru"),
    TURKISH("ru"),
}

interface TranslationService{
    suspend fun translate(sourceText: String,
                  sourceLang: LanguageCode,
                  targetLang: LanguageCode): String
}

class TranslateUseCase (private val translator: TranslationService) {
    suspend fun translate(sourceText: String,
                  sourceLang: LanguageCode = LanguageCode.AUTO_DETECT,
                  targetLang: LanguageCode): String =
        translator.translate(sourceText, sourceLang, targetLang)
}