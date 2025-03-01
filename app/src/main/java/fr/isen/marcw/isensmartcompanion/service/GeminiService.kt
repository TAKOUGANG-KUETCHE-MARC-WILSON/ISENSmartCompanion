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
            Log.d("GeminiService", "Envoi de la requête : $userInput")
            val response = withContext(Dispatchers.IO) {
                model.generateContent(Content(parts = listOf(TextPart(userInput))))
            }
            val responseText = response.text ?: "Aucune réponse reçue."
            Log.d("GeminiService", "Réponse reçue : $responseText")
            responseText
        } catch (e: Exception) {
            Log.e("GeminiService", "Erreur lors de l'appel à Gemini", e)
            "Erreur lors de l'analyse : ${e.localizedMessage}"
        }
    }


}

