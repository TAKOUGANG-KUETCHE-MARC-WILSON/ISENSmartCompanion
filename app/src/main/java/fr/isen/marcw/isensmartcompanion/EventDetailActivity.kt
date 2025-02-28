package fr.isen.marcw.isensmartcompanion.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.marcw.isensmartcompanion.model.Event
import fr.isen.marcw.isensmartcompanion.ui.theme.ISENSmartCompanionTheme

class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Récupération de l'objet Event depuis l'Intent
        val event = intent.getSerializableExtra("event") as? Event

        setContent {
            ISENSmartCompanionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    event?.let { EventDetailScreen(it, onBack = { finish() }) }
                        ?: Text("Aucun événement trouvé")
                }
            }
        }
    }
}

@Composable
fun EventDetailScreen(event: Event, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF5F5F5)), // Fond gris clair
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Spacer pour pousser l'image un peu plus bas
        Spacer(modifier = Modifier.height(32.dp)) // Ajuste cette valeur selon tes besoins

        // Image bien positionnée
        Image(
            painter = painterResource(id = event.imageRes),
            contentDescription = event.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(12.dp)) // Coins arrondis pour l'image
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Carte contenant les détails de l'événement
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = event.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD32F2F) // Rouge ISEN
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "📅 ${event.date}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                )
                Text(
                    text = "📍 ${event.location}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = event.description,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Bouton retour stylisé
        Button(
            onClick = { onBack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)) // Rouge ISEN
        ) {
            Text(text = "Retour", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

