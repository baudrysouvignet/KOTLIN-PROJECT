package com.example.jeudedes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FilmEntity(
    @PrimaryKey val id: String,
    val titre: String,
    val annee: String,
    val realisateur: String,
    val genre: String,
    val synopsis: String
)
