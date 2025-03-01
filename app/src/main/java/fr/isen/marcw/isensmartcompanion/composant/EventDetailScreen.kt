package fr.isen.marcw.isensmartcompanion.composant

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import fr.isen.marcw.isensmartcompanion.model.Event
import fr.isen.marcw.isensmartcompanion.model.NotificationWorker
import java.util.concurrent.TimeUnit


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(event: Event, navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("notifications_prefs", Context.MODE_PRIVATE)

    // ✅ Récupérer la liste des événements épinglés
    val notifiedEvents = sharedPreferences.getStringSet("notified_events", emptySet()) ?: emptySet()
    var isNotified by remember { mutableStateOf(event.id in notifiedEvents) }

    fun saveNotificationState(eventId: String, isNotified: Boolean) {
        val editor = sharedPreferences.edit()
        val updatedSet = sharedPreferences.getStringSet("notified_events", mutableSetOf())?.toMutableSet() ?: mutableSetOf()

        if (isNotified) {
            updatedSet.add(eventId) // ✅ Ajouter l'événement épinglé
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = event.title) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")
                    }
                },
                actions = {
                    // ✅ Icône cloche pour activer/désactiver les notifications
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
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            Text(text = event.title, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Date: ${event.date}", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = event.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}






