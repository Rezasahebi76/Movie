package com.android.movie.data.datasource.local

import androidx.paging.PagingSource
import com.android.movie.database.entities.MovieEntity

interface MoviesLocalDataSource {

   suspend fun insertMovies(movies:List<MovieEntity>)

   suspend fun refreshAllMovies(movies: List<MovieEntity>)

    fun getMoviePagingDataSource():PagingSource<Int,MovieEntity>
}