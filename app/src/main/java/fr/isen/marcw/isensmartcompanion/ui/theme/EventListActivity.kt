package fr.isen.marcw.isensmartcompanion.ui.theme


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import fr.isen.marcw.isensmartcompanion.composant.EventsScreen

class EventListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventsScreen()
        }
    }
}
