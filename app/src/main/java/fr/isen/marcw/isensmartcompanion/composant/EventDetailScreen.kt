package fr.isen.marcw.isensmartcompanion.composant

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.isen.marcw.isensmartcompanion.model.eventList

@Composable
fun EventDetailScreen(navController: NavController, eventId: String) {
    val event = eventList.find { it.id.toString() == eventId }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        event?.let {
            Text(text = it.title, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Date : ${it.date}", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it.description, fontSize = 16.sp)
        } ?: run {
            Text(text = "Événement non trouvé", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Retour")
        }
    }
}