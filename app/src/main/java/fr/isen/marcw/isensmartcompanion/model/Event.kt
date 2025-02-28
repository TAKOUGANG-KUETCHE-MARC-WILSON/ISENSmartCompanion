package fr.isen.marcw.isensmartcompanion.model

import androidx.annotation.DrawableRes
import fr.isen.marcw.isensmartcompanion.R

data class Event(
    val id: String,              // Identifiant unique de l'événement
    val category: String,     // Catégorie de l'événement
    val title: String,        // Titre de l'événement
    val description: String,  // Description de l'événement
    val date: String,         // Date de l'événement
    val location: String,     // Lieu de l'événement
    @DrawableRes val imageRes: Int // Ressource drawable pour l'image associée
)

// Fonction pour récupérer les événements par défaut
fun getDefaultEvents(): List<Event> {
    return listOf(
        Event(
            id = "1",
            category = "Soirée",
            title = "Soirée BDE",
            description = "Une soirée organisée par le BDE.",
            date = "15 Mars 2025",
            location = "Salle des fêtes ISEN",
            imageRes = R.drawable.party
        ),
        Event(
            id = "2",
            category = "Gala",
            title = "Gala annuel",
            description = "Le gala annuel de l'ISEN.",
            date = "25 Avril 2025",
            location = "Grand Hôtel de Lille",
            imageRes = R.drawable.gala
        ),
        Event(
            id = "3",
            category = "Cohésion",
            title = "Journée de cohésion",
            description = "Une journée pour renforcer les liens.",
            date = "10 Mai 2025",
            location = "Campus ISEN",
            imageRes = R.drawable.cohesion
        ),
        Event(
            id = "4",
            category = "Détente",
            title = "After Work",
            description = "Un moment de détente après les cours.",
            date = "5 Juin 2025",
            location = "Bar du centre-ville",
            imageRes = R.drawable.after_work
        ),
        Event(
            id = "5",
            category = "Intégration",
            title = "WEI",
            description = "Week-end d'intégration pour les nouveaux étudiants.",
            date = "1er Septembre 2025",
            location = "Lieu à définir",
            imageRes = R.drawable.wei
        ),
        Event(
            id = "6",
            category = "E-Sport",
            title = "Tournoi E-Sport",
            description = "Affrontez vos camarades sur les jeux les plus populaires.",
            date = "28 Juin 2025",
            location = "Salle gaming ISEN",
            imageRes = R.drawable.esport
        ),
        Event(
            id = "7",
            category = "Sport",
            title = "Sortie Ski",
            description = "Week-end de ski organisé par l’école.",
            date = "19 Septembre 2025",
            location = "Station de ski des Alpes",
            imageRes = R.drawable.ski
        ),
        Event(
            id = "8",
            category = "Hackathon",
            title = "Hackathon ISEN",
            description = "24h de code et d’innovation en équipe !",
            date = "5 Juin 2025",
            location = "Salle informatique ISEN",
            imageRes = R.drawable.hackathon
        ),
        Event(
            id = "9",
            category = "Conférence",
            title = "Conférence Tech",
            description = "Intervention d’experts du numérique et de l’IA.",
            date = "20 Juin 2025",
            location = "Amphithéâtre ISEN",
            imageRes = R.drawable.conference
        ),
    )
}



// Liste des événements par défaut
val eventList = getDefaultEvents()

// Fonction pour convertir un EventDto en Event avec une image par défaut
fun EventDto.toEvent(): Event {
    val imageRes = when (category) {
        "soirée" -> R.drawable.party
        "gala" -> R.drawable.gala
        "Vie associative" -> R.drawable.team
        "BDE" -> R.drawable.hackathon
        "BDS" -> R.drawable.conference
        "Professionnel" -> R.drawable.conference
        "Concours" -> R.drawable.conference
        "Institutionnel" -> R.drawable.conference
        "International" -> R.drawable.conference
        "Technologique" -> R.drawable.conference
        "e-sport" -> R.drawable.esport
        "sport" -> R.drawable.ski
        else -> R.drawable.team // Image par défaut
    }
    return Event(id, category, title, description, date, location, imageRes)
}
