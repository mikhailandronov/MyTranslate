package ru.andronov.translate.app.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.andronov.translate.app.usecases.HistoryUseCase
import ru.andronov.translate.app.usecases.LanguageCode
import ru.andronov.translate.app.usecases.TranslateUseCase

class TranslationViewModel(
    private val translateUseCase: TranslateUseCase,
    private val saveHistoryUseCase: HistoryUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TranslationUiState())
    val uiState: StateFlow<TranslationUiState> = _uiState

    fun updateInputText(newText: String) {
        _uiState.update { it.copy(inputText = newText) }
    }

    fun clearInputText() {
        _uiState.update { it.copy(inputText = "", translatedText = "") }
    }

    fun swapLanguages() {
        _uiState.update {
            it.copy(
                sourceLang = it.targetLang,
                targetLang = it.sourceLang
            )
        }
    }

    fun translate() {
        viewModelScope.launch {
            val result =
                translateUseCase.translate(
                    sourceLang = _uiState.value.sourceLang,
                    targetLang = _uiState.value.targetLang,
                    sourceText = _uiState.value.inputText
                )

            _uiState.update {
                it.copy(translatedText = result)
            }

            saveHistoryUseCase.save(
                _uiState.value.inputText,
                _uiState.value.translatedText
            )
        }
    }
}

data class TranslationUiState(
    val sourceLang: LanguageCode = LanguageCode.ENGLISH,
    val targetLang: LanguageCode = LanguageCode.RUSSIAN,
    val inputText: String = "",
    val translatedText: String = ""
)