package com.example.jeudedes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FilmDao {
    @Query("SELECT * FROM filmentity")
    suspend fun getFilms(): List<FilmEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilm(film: FilmEntity)

    @Query("DELETE FROM filmentity WHERE id = :id")
    suspend fun deleteFilm(id: String)
}
