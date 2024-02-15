package com.android.movie.data.datasource.local.movie

import androidx.paging.PagingSource
import com.android.movie.database.daos.MovieDao
import com.android.movie.database.entities.MovieEntity
import javax.inject.Inject

class MoviesLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao,
) : MoviesLocalDataSource {

    override suspend fun insertMovies(movies: List<MovieEntity>) {
        movieDao.insertMovies(movies)
    }

    override suspend fun refreshAllMovies(movies: List<MovieEntity>) {
        movieDao.refreshMovies(movies)
    }

    override fun getMoviePagingDataSource(): PagingSource<Int, MovieEntity> =
        movieDao.getPagingSource()

}