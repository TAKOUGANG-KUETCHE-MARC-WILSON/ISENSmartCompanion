package fr.isen.marcw.isensmartcompanion

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import fr.isen.marcw.isensmartcompanion.composant.BottomNavigationBar
import fr.isen.marcw.isensmartcompanion.composant.EventDetailScreen
import fr.isen.marcw.isensmartcompanion.composant.EventsScreen
import fr.isen.marcw.isensmartcompanion.composant.HistoryScreen
import fr.isen.marcw.isensmartcompanion.composant.MainScreen
import fr.isen.marcw.isensmartcompanion.ui.theme.EventListActivity
import fr.isen.marcw.isensmartcompanion.ui.theme.ISENSmartCompanionTheme


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Scaffold(bottomBar = { BottomNavigationBar(navController) }) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { MainScreen(navController) }
                composable("events") { EventsScreen(navController) }
                composable("history") { HistoryScreen(navController) }
                composable("eventDetail/{eventId}",
                    arguments = listOf(navArgument("eventId") { type = NavType.StringType }) 
                ) { backStackEntry ->
                    val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
                    EventDetailScreen(navController, eventId)
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
                    AppNavigation() // Passage de l'instance de l'Activity
                }
            }
        }
    }

    fun openEventList() {
        startActivity(Intent(this, EventListActivity::class.java))
    }
}

@Composable
fun MainScreen(navController: NavController, mainActivity: MainActivity) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { navController.navigate("events") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Voir les événements")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { mainActivity.openEventList() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Ouvrir EventListActivity")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    ISENSmartCompanionTheme {
        val navController = rememberNavController()
        MainScreen(navController, MainActivity()) // Ajout de MainActivity
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEventsScreen() {
    ISENSmartCompanionTheme {
        EventsScreen(rememberNavController()) // Correction de l'appel
    }
}
