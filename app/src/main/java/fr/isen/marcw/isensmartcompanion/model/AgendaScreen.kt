package fr.isen.marcw.isensmartcompanion.model

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fr.isen.marcw.isensmartcompanion.composant.Course
import fr.isen.marcw.isensmartcompanion.composant.EventItem
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AgendaScreen(events: List<Event>, courses: List<Course>) {
    var selectedDate by remember { mutableStateOf(Date()) }
    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header with selected date and date picker
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Agenda - ${dateFormat.format(selectedDate)}",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            )
            Button(
                onClick = {
                    showDatePickerDialog(context) { newDate ->
                        selectedDate = newDate
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Changer la date", color = Color.White)
            }
        }

        // Courses Section
        Text(
            text = "Mes cours",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(courses) { course ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE6E6)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = course.title,
                                style = MaterialTheme.typography.titleLarge.copy(color = Color.Black)
                            )
                            Text(
                                text = course.time,
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                            )
                        }
                        Text(
                            text = course.location,
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.DarkGray)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Events Section
        Text(
            text = "Mes événements suivis",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val sharedPreferences =
            LocalContext.current.getSharedPreferences("notifications_prefs", Context.MODE_PRIVATE)
        val notifiedEvents = sharedPreferences.getStringSet("notified_events", emptySet()) ?: emptySet()
        val followedEvents = events.filter { it.id in notifiedEvents }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(followedEvents) { event ->
                EventItem(event)
            }
        }
    }
}

// Helper Function for DatePicker
fun showDatePickerDialog(context: Context, onDateSelected: (Date) -> Unit) {
    val calendar = Calendar.getInstance()
    DatePickerDialog(
        context,
        { _, year, month, day ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, month, day)
            onDateSelected(selectedCalendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}
