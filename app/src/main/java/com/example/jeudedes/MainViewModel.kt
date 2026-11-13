package com.example.jeudedes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val repository = FilmRepository()

    val database = Room.databaseBuilder(application, AppDatabase::class.java, "database-name")
        .fallbackToDestructiveMigration()
        .build()
    val dao = database.filmDao()

    val films = MutableStateFlow<List<FilmSearch>>(emptyList())

    val filmDetail = MutableStateFlow<FilmDetail?>(null)

    val searchQuery = MutableStateFlow("")

    fun searchFilms() {
        viewModelScope.launch {
            val query = searchQuery.value
            // films enregistrés localement dont le titre correspond à la recherche
            val filmsLocaux = dao.getFilms()
                .filter { it.titre.contains(query, ignoreCase = true) }
                .map {
                    FilmSearch(
                        title = it.titre,
                        year = it.annee,
                        imdbID = it.id,
                        type = "Local"
                    )
                }
            films.value = filmsLocaux + repository.searchFilms(query)
        }
    }

    fun getFilmDetail(imdbId: String) {
        filmDetail.value = null
        viewModelScope.launch {
            val filmLocal = dao.getFilms().find { it.id == imdbId }
            filmDetail.value = if (filmLocal != null) {
                FilmDetail(
                    title = filmLocal.titre,
                    year = filmLocal.annee,
                    director = filmLocal.realisateur,
                    genre = filmLocal.genre,
                    plot = filmLocal.synopsis,
                    imdbID = filmLocal.id
                )
            } else {
                repository.getFilmDetail(imdbId)
            }
        }
    }

    fun addFilm(titre: String, annee: String, realisateur: String, genre: String, synopsis: String) {
        viewModelScope.launch {
            val filmEntity = FilmEntity(
                id = "local-" + UUID.randomUUID().toString(),
                titre = titre,
                annee = annee,
                realisateur = realisateur,
                genre = genre,
                synopsis = synopsis
            )
            dao.insertFilm(filmEntity)
        }
    }
}
