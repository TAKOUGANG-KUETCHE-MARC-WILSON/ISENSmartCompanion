package fr.isen.marcw.isensmartcompanion.composant

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import fr.isen.marcw.isensmartcompanion.model.Event
import fr.isen.marcw.isensmartcompanion.model.NotificationWorker
import fr.isen.marcw.isensmartcompanion.ui.theme.EventsViewModel
import java.util.concurrent.TimeUnit

@Composable
fun EventListScreen() {
    val viewModel = remember { EventsViewModel() }
    val eventsList = viewModel.events.collectAsState(initial = emptyList())

    LaunchedEffect(true) {
        viewModel.fetchEvents()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)) // Light gray background
            .padding(16.dp)
    ) {
        Text(
            text = "Liste des événements",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            ),
            color = Color(0xFFD32F2F), // Primary red color
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(eventsList.value) { event ->
                EventItem(event)
            }
        }
    }
}

@Composable
fun EventItem(event: Event) {
    val context = LocalContext.current
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("notifications_prefs", Context.MODE_PRIVATE)

    // ✅ Récupérer la liste des événements notifiés sous forme de Set<String>
    val notifiedEvents = sharedPreferences.getStringSet("notified_events", emptySet()) ?: emptySet()

    // ✅ Vérifier si l'événement est notifié
    var isNotified by remember(event.id) { mutableStateOf(event.id in notifiedEvents) }

    fun saveNotificationState(eventId: String, isNotified: Boolean) {
        val editor = sharedPreferences.edit()
        val updatedSet = sharedPreferences.getStringSet("notified_events", mutableSetOf())?.toMutableSet() ?: mutableSetOf()

        if (isNotified) {
            updatedSet.add(eventId) // ✅ Ajouter l'ID de l'événement notifié
        } else {
            updatedSet.remove(eventId) // ❌ Supprimer si désactivé
        }

        editor.putStringSet("notified_events", updatedSet)
        editor.apply()
    }

    fun scheduleNotification(eventId: String, title: String, date: String) {
        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(10, TimeUnit.SECONDS) // Notification après 10 sec
            .setInputData(
                workDataOf(
                    "eventId" to eventId,
                    "eventTitle" to title,
                    "eventDate" to date
                )
            )
            .build()
        WorkManager.getInstance(context).enqueue(workRequest)
    }

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = event.title, style = MaterialTheme.typography.titleLarge, color = Color(0xFFD32F2F))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Date: ${event.date}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }

            // ✅ Icône cliquable pour activer/désactiver la notification
            IconButton(
                onClick = {
                    isNotified = !isNotified
                    saveNotificationState(event.id, isNotified)

                    if (isNotified) {
                        scheduleNotification(event.id, event.title, event.date)
                    }
                }
            ) {
                Icon(
                    imageVector = if (isNotified) Icons.Filled.Notifications else Icons.Filled.NotificationsOff,
                    contentDescription = if (isNotified) "Notification activée" else "Notification désactivée",
                    tint = if (isNotified) Color.Red else Color.Gray
                )
            }
        }
    }
}


