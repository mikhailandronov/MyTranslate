package ru.andronov.translate.app.adapters

import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import ru.andronov.translate.app.usecases.LanguageCode
import ru.andronov.translate.app.usecases.TranslationService
import java.net.URL

class AndroidLogger : Logger {
    override fun log(message: String) {
        Log.d("MyKtorClient", message)
    }
}

class FTApiTranslator(val endpoint: URL) : TranslationService {

    override suspend fun translate(
        sourceText: String,
        sourceLang: LanguageCode,
        targetLang: LanguageCode
    ): String {
        var result = ""

        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(Logging) {
                logger = AndroidLogger()
                level = LogLevel.BODY
                filter { request ->
                    request.url.host.contains("ftapi")
                }
            }
        }.use { client ->
            runBlocking {
                val response = client.get(endpoint) {
                    url {
                        parameters.append("sl", sourceLang.code)
                        parameters.append("dl", targetLang.code)
                        parameters.append("text", sourceText)
                    }
                }

                try {
                    val translatorResponse: TranslationResponse = response.body()
                    result = translatorResponse
                        .translations
                        ?.possibleTranslations
                        ?.firstOrNull()
                        ?: ""
                } catch (e: NoTransformationFoundException) {
                    result = e.message
                }
            }
        }

        return "Переведенная строка с сервиса ${endpoint.host}.\n" +
                "Перевод c ${sourceLang.code} на ${targetLang.code}.\n" +
                result
    }
}

@Serializable
data class TranslationResponse(
    val pronunciation: Pronunciation?,
    val translations: Translations?,
)

@Serializable
data class Pronunciation(
    @SerialName("source-text-audio") val sourceTextAudio: String?,
    @SerialName("destination-text-audio") val destinationTextAudio: String?,
)

@Serializable
data class Translations(
    @SerialName("possible-translations") val possibleTranslations: List<String>?,
)
