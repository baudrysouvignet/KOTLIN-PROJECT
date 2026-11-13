package com.example.jeudedes

class FilmListDestination

data class FilmDetailDestination(val imdbId: String)

class AddFilmDestination

data class PosterFullScreenDestination(val posterUrl: String, val title: String)
