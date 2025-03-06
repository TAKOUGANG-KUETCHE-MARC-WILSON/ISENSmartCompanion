package fr.isen.marcw.isensmartcompanion.composant

data class Course(
    val id: String,
    val title: String,
    val time: String,
    val location: String
)

fun getDefaultCourses(): List<Course> {
    return listOf(
        Course("1", "Mathématiques", "08:00 - 10:00", "Salle A"),
        Course("2", "Physique", "10:15 - 12:15", "Salle B"),
        Course("3", "Programmation", "14:00 - 16:00", "Salle C"),
    )
}