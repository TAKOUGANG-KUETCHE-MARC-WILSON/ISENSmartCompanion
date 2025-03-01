package fr.isen.marcw.isensmartcompanion.composant

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import java.text.SimpleDateFormat
import java.util.*
import fr.isen.marcw.isensmartcompanion.navigation.HistoryViewModel


@Composable
fun HistoryScreen(viewModel: HistoryViewModel) {
    val history by viewModel.history.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadHistory()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Historique",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Red,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(history) { item ->
                val dateFormatted = formatDate(item.timestamp) // Utilisez item.timestamp ici
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), // Réduit l'espace vertical
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) { // Réduit le padding interne du Card
                        Text(text = "Vous: ${item.question}", fontWeight = FontWeight.Bold, color = Color.Black)
                        Spacer(modifier = Modifier.height(4.dp)) // Réduit l'espace entre le message et la réponse
                        Text(text = "IA: ${item.response}", color = Color.DarkGray, fontSize = 14.sp)

                        Spacer(modifier = Modifier.height(4.dp)) // Réduit l'espace entre la réponse et la date

                        // Affichage de la date et de l'heure
                        Text(text = "Date et Heure: $dateFormatted", fontSize = 12.sp, color = Color.Gray)

                        Spacer(modifier = Modifier.height(12.dp)) // Réduit l'espace avant le bouton

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = { viewModel.deleteHistoryById(item.id) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text("Supprimer", color = Color.White)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.clearHistory() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Effacer tout l'historique", color = Color.White)
        }
    }
}

// Fonction pour formater la date et l'heure
fun formatDate(timestamp: Long): String {
    // Formater la date et l'heure (jour/mois/année heure:minute:seconde)
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return dateFormat.format(Date(timestamp))
}




