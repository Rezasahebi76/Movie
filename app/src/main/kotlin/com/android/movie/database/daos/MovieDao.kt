package com.android.movie.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.android.movie.database.entities.MovieEntity

@Dao
interface MovieDao {

    @Upsert
    fun insertMovies(movieEntity: MovieEntity)

    @Query("DELETE FROM movies")
    fun clearAllMovies()
}