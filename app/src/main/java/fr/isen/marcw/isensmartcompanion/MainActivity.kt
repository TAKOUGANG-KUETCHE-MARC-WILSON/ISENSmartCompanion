package fr.isen.marcw.isensmartcompanion

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import fr.isen.marcw.isensmartcompanion.composant.BottomNavigationBar
import fr.isen.marcw.isensmartcompanion.composant.EventsScreen
import fr.isen.marcw.isensmartcompanion.composant.HistoryScreen
import fr.isen.marcw.isensmartcompanion.composant.MainScreen
import fr.isen.marcw.isensmartcompanion.composant.NotificationsScreen
import fr.isen.marcw.isensmartcompanion.navigation.AppDatabase
import fr.isen.marcw.isensmartcompanion.navigation.HistoryRepository
import fr.isen.marcw.isensmartcompanion.navigation.HistoryViewModel
import fr.isen.marcw.isensmartcompanion.navigation.HistoryViewModelFactory
import fr.isen.marcw.isensmartcompanion.ui.theme.EventListActivity
import android.Manifest
import android.content.Context
import fr.isen.marcw.isensmartcompanion.composant.getDefaultCourses
import fr.isen.marcw.isensmartcompanion.model.AgendaScreen
import fr.isen.marcw.isensmartcompanion.ui.theme.EventsViewModel
import fr.isen.marcw.isensmartcompanion.ui.theme.ISENSmartCompanionTheme


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current // Correctement utilisé ici
    val database = AppDatabase.getDatabase(context)
    val historyRepository = HistoryRepository(database.historyDao())
    val historyViewModel: HistoryViewModel =
        viewModel(factory = HistoryViewModelFactory(historyRepository))

    Scaffold(bottomBar = { BottomNavigationBar(navController) }) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { MainScreen(navController) }
                composable("events") { EventsScreen(navController) }
                composable("history") { HistoryScreen(historyViewModel) }
                composable("notifications") {
                    val sharedPreferences = LocalContext.current.getSharedPreferences("notifications_prefs", Context.MODE_PRIVATE)

                    val eventsViewModel: EventsViewModel = viewModel()
                    val eventsList by eventsViewModel.events.collectAsState(initial = emptyList())
                    // 🔥 Récupérer uniquement les événements épinglés (avec notification activée)
                    val notifiedEvents = eventsList.filter { sharedPreferences.getBoolean(it.id, false) }

                    NotificationsScreen(navController, notifiedEvents)
                }
                composable(route = "agenda") {
                    val eventsViewModel = remember { EventsViewModel() }
                    val events by eventsViewModel.events.collectAsState()
                    val courses = getDefaultCourses() // Liste statique ou source dynamique pour les cours

                    AgendaScreen(events = events, courses = courses)
                }

            }
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ISENSmartCompanionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation() // Pas de passage explicite de l'Activity ici
                }
            }
        }
        // Demande de permission pour Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }
    }

    fun openEventList() {
        startActivity(Intent(this, EventListActivity::class.java))
    }
}
