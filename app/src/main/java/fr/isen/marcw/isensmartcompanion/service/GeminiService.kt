package fr.isen.marcw.isensmartcompanion.service

import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.TextPart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GeminiService {
    private const val API_KEY = "AIzaSyCwefT6Y79ADYSc8pCTtyaFbUyO9b01wg8"

    private val model = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = API_KEY
    )

    suspend fun getResponse(userInput: String): String {
        return try {
            val response = withContext(Dispatchers.IO) {
                model.generateContent(Content(parts = listOf(TextPart(userInput))))
            }
            response.text ?: "Aucune réponse reçue."
        } catch (e: Exception) {
            Log.e("GeminiService", "Erreur: ${e.message}")
            "Erreur lors de l'analyse."
        }
    }
}
