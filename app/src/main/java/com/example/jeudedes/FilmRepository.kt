package com.example.jeudedes

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class FilmRepository {

    private val apiKey = "54f58b35"

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = object : io.ktor.client.plugins.logging.Logger {
                override fun log(message: String) {
                    Log.d("Ktor-Logger", message)
                }
            }
        }
    }

    suspend fun searchFilms(query: String): List<FilmSearch> {
        Log.d("FilmRepository", "Recherche de: $query")
        return try {
            val response: SearchResponse = client.get("http://www.omdbapi.com/") {
                parameter("s", query)
                parameter("apikey", apiKey)
            }.body()
            Log.d("FilmRepository", "Résultats: ${response.search.size} films")
            response.search
        } catch (e: Exception) {
            Log.e("FilmRepository", "Erreur lors de la recherche", e)
            emptyList()
        }
    }
    suspend fun getFilmDetail(imdbId: String): FilmDetail {
        Log.d("FilmRepository", "Détails pour: $imdbId")
        return try {
            client.get("http://www.omdbapi.com/") {
                parameter("i", imdbId)
                parameter("apikey", apiKey)
            }.body()
        } catch (e: Exception) {
            Log.e("FilmRepository", "Erreur lors de la récupération des détails", e)
            FilmDetail()
        }
    }
}
