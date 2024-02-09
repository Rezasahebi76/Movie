package com.android.movie.data.datasource.local

import com.android.movie.database.daos.MovieDao
import com.android.movie.database.entities.MovieEntity
import javax.inject.Inject

class MoviesLocalDataSourceImpl @Inject constructor (private val movieDao: MovieDao):MoviesLocalDataSource{

    override fun insertMovies(movies:List<MovieEntity>){
        movieDao.insertMovies(movies)
    }

    override fun clearAllMovies(){
        movieDao.clearAllMovies()
    }
}