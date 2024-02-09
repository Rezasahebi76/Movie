package com.android.movie.data.datasource.local

import androidx.paging.PagingSource
import com.android.movie.common.dispatcher.Dispatcher
import com.android.movie.common.dispatcher.MovieDispatchers
import com.android.movie.database.daos.MovieDao
import com.android.movie.database.entities.MovieEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao,
    @Dispatcher(MovieDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher
) : MoviesLocalDataSource {

    override suspend fun insertMovies(movies: List<MovieEntity>) = withContext(ioDispatcher){
        movieDao.insertMovies(movies)
    }

    override suspend fun refreshAllMovies(movies: List<MovieEntity>) = withContext(ioDispatcher){
        movieDao.refreshMovies(movies)
    }

    override fun getMoviePagingDataSource(): PagingSource<Int, MovieEntity> {
        return movieDao.getPagingSource()
    }
}