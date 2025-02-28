package fr.isen.marcw.isensmartcompanion.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.isen.marcw.isensmartcompanion.service.EventApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventsViewModel : ViewModel() {
    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    private val apiService = EventApiService.create()

    // Méthode pour charger les événements depuis l'API
    fun fetchEvents() {
        viewModelScope.launch {
            try {
                val eventDtos: List<EventDto> = apiService.getEvents() // Appel réseau
                Log.d("Events", "Événements reçus : $eventDtos") // Log des données reçues

                val events = eventDtos.map { it.toEvent() } // Conversion en Event
                _events.update { events } // Mise à jour de l'état

                Log.d("Events", "Événements convertis : $events") // Vérifier après conversion
            } catch (e: Exception) {
                Log.e("Events", "Erreur lors de la récupération des événements : ${e.message}")
            }
        }
    }

}
