package fr.isen.marcw.isensmartcompanion.service

import fr.isen.marcw.isensmartcompanion.composant.EventDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface EventApiService {
    @GET("events.json")
    suspend fun getEvents(): List<EventDto>

    companion object {
        private const val BASE_URL = "https://isen-smart-companion-default-rtdb.europe-west1.firebasedatabase.app/"

        fun create(): EventApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EventApiService::class.java)
        }
    }
}
