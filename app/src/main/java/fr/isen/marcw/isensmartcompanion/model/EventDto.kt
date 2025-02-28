package fr.isen.marcw.isensmartcompanion.model

data class EventDto(
    val id: String,
    val category: String,
    val title: String,
    val description: String,
    val date: String,
    val location: String
)
