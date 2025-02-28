package fr.isen.marcw.isensmartcompanion.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.isen.marcw.isensmartcompanion.model.Event
import fr.isen.marcw.isensmartcompanion.model.toEvent
import fr.isen.marcw.isensmartcompanion.service.EventApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventsViewModel : ViewModel() {
    private val apiService = EventApiService.create()
    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    fun fetchEvents() {
        viewModelScope.launch {
            try {
                val eventDtos = apiService.getEvents()
                _events.value = eventDtos.map { it.toEvent() }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
