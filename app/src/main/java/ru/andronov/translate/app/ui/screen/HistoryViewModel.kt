package ru.andronov.translate.app.ui.screen

import androidx.lifecycle.ViewModel
import ru.andronov.translate.app.usecases.HistoryUseCase

class HistoryViewModel (
    private val useCase: HistoryUseCase
): ViewModel() {
    fun getHistory() = useCase.getHistory()
}