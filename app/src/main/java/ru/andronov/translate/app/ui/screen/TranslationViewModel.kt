package ru.andronov.translate.app.ui.screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TranslationViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(TranslationUiState())
    val uiState: StateFlow<TranslationUiState> = _uiState

    fun updateInputText(newText: String){
        _uiState.update { it.copy(inputText = newText) }
    }

    fun clearInputText(){
        _uiState.update { it.copy(inputText = "", translatedText = "") }
    }

    fun swapLanguages(){
        _uiState.update {
            it.copy(
                sourceLang = it.targetLang,
                targetLang = it.sourceLang
            )
        }
    }

    fun translate(){
        // TODO: реализовать перевод текста
        _uiState.update {
            it.copy(translatedText = "Здесь будет отображаться перевод текста:\n${it.inputText}")
        }
    }
}

data class TranslationUiState(
    val sourceLang: String = "English",
    val targetLang: String = "Russian",
    val inputText: String = "",
    val translatedText: String = ""
)