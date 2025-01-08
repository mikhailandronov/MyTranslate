package ru.andronov.translate.app.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.andronov.translate.app.usecases.TranslationHistory
import java.text.SimpleDateFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = koinViewModel()
) {
    val historyList = viewModel.getHistory().collectAsState(initial = emptyList())

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(title = { Text("History") })

        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(historyList.value){ history ->
                HistoryItem(history)
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun HistoryItem(historyRecord: TranslationHistory) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = "Source: ${historyRecord.sourceText}")
        Text(text = "Translation: ${historyRecord.translatedText}")
        Text(text = "Timestamp: ${SimpleDateFormat().format(historyRecord.timestamp)}")
    }
}