package com.android.movie.database.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.android.movie.database.entities.MovieEntity

@Dao
interface MovieDao {

    @Transaction
    suspend fun refreshMovies(movies: List<MovieEntity>){
        clearAllMovies()
        insertMovies(movies)
    }

    @Insert
    suspend fun insertMovies(movieEntity: List<MovieEntity>)

    @Query("SELECT * FROM movies")
    fun getPagingSource(): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM movies")
    suspend fun clearAllMovies()
}