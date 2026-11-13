package com.example.jeudedes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmSearch(
    @SerialName("Title") val title: String = "",
    @SerialName("Year") val year: String = "",
    @SerialName("imdbID") val imdbID: String = "",
    @SerialName("Type") val type: String = "",
    @SerialName("Poster") val poster: String = ""
)

@Serializable
data class SearchResponse(
    @SerialName("Search") val search: List<FilmSearch> = emptyList(),
    @SerialName("totalResults") val totalResults: String = "0",
    @SerialName("Response") val response: String = "False"
)

@Serializable
data class FilmDetail(
    @SerialName("Title") val title: String = "",
    @SerialName("Year") val year: String = "",
    @SerialName("Rated") val rated: String = "",
    @SerialName("Released") val released: String = "",
    @SerialName("Runtime") val runtime: String = "",
    @SerialName("Genre") val genre: String = "",
    @SerialName("Director") val director: String = "",
    @SerialName("Writer") val writer: String = "",
    @SerialName("Actors") val actors: String = "",
    @SerialName("Plot") val plot: String = "",
    @SerialName("Language") val language: String = "",
    @SerialName("Country") val country: String = "",
    @SerialName("Poster") val poster: String = "",
    @SerialName("imdbRating") val imdbRating: String = "",
    @SerialName("imdbID") val imdbID: String = ""
)
