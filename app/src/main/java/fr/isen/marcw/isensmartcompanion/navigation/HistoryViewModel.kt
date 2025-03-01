package fr.isen.marcw.isensmartcompanion.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {
    private val _history = MutableStateFlow<List<HistoryEntity>>(emptyList())
    val history = _history.asStateFlow()

    fun loadHistory() {
        viewModelScope.launch {
            _history.value = repository.getAllHistory()
        }
    }

    fun deleteHistoryById(id: Int) {
        viewModelScope.launch {
            repository.deleteHistoryById(id)
            loadHistory()
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearHistory()
            loadHistory()
        }
    }
}
