package fr.isen.marcw.isensmartcompanion.composant

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.isen.marcw.isensmartcompanion.composant.Event
import fr.isen.marcw.isensmartcompanion.ui.theme.EventsViewModel

@Composable
fun EventListScreen() {
    val viewModel = remember { EventsViewModel() }
    val eventsList = viewModel.events.collectAsState(initial = emptyList())

    LaunchedEffect(true) {
        viewModel.fetchEvents()
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Liste des événements",
            style = MaterialTheme.typography.headlineLarge, // Correction ici
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(eventsList.value) { event ->
                EventItem(event)
            }
        }
    }
}

@Composable
fun EventItem(event: Event) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = event.title, style = MaterialTheme.typography.titleMedium) // Correction ici
            Text(text = event.date)
            Text(text = event.location)
        }
    }
}