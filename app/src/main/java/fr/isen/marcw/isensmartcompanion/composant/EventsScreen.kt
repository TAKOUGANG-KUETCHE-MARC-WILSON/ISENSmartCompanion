package fr.isen.marcw.isensmartcompanion.composant

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.isen.marcw.isensmartcompanion.activities.EventDetailActivity
import fr.isen.marcw.isensmartcompanion.model.Event
import fr.isen.marcw.isensmartcompanion.ui.theme.EventsViewModel

@Composable
fun EventsScreen(navController: NavController? = null) {
    val viewModel: EventsViewModel = viewModel()
    val eventList by viewModel.events.collectAsState()
    val context = LocalContext.current // Contexte Android pour ouvrir une activité

    LaunchedEffect(Unit) {
        Log.d("EventsScreen", "fetchEvents() est appelée")
        viewModel.fetchEvents()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Événements ISEN",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD32F2F),
            modifier = Modifier.padding(8.dp)
        )
        if (eventList.isEmpty()) {
            Text(
                text = " ",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(eventList) { event ->
                    EventItem(event = event, onClick = {
                        val intent = Intent(context, EventDetailActivity::class.java)
                        intent.putExtra("event", event) // On passe l'objet Event
                        context.startActivity(intent)
                    })
                }
            }
        }
    }
}

@Composable
fun EventItem(event: Event, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }, // On ouvre `EventDetailActivity` au clic
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = event.imageRes),
                contentDescription = event.title,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 12.dp)
            )
            Column {
                Text(
                    text = event.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD32F2F)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = event.date,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
