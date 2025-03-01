package fr.isen.marcw.isensmartcompanion.navigation

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val question: String,
    val response: String,
    val timestamp: Long = System.currentTimeMillis()
)