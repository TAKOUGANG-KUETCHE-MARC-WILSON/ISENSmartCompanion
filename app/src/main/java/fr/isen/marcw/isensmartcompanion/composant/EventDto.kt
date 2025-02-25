package fr.isen.marcw.isensmartcompanion.composant

data class EventDto(
    val id: Int,
    val category: String,
    val title: String,
    val description: String,
    val date: String,
    val location: String
)
