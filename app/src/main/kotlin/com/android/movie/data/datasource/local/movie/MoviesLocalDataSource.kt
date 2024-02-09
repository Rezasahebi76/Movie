package com.android.movie.data.datasource.local.movie

import androidx.paging.PagingSource
import com.android.movie.database.entities.MovieEntity

interface MoviesLocalDataSource {

   suspend fun insertMovies(movies:List<MovieEntity>)

   suspend fun refreshAllMovies(movies: List<MovieEntity>)

    fun getMoviePagingDataSource():PagingSource<Int,MovieEntity>
}