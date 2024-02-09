package com.android.movie.data.datasource.local

import com.android.movie.database.entities.MovieEntity

interface MoviesLocalDataSource {

    fun insertMovies(movies:List<MovieEntity>)

    fun clearAllMovies()
}