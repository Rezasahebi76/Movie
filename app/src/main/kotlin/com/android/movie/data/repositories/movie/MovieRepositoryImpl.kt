package com.android.movie.data.repositories.movie

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.android.movie.data.datasource.local.movie.MoviesLocalDataSource
import com.android.movie.data.mapper.toMovie
import com.android.movie.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val localDataSource: MoviesLocalDataSource,
    private val remoteMediator: MovieRemoteMediator
) : MovieRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getMoviePagingData(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(20),
            remoteMediator = remoteMediator,
            pagingSourceFactory = localDataSource::getMoviePagingDataSource
        ).flow.map { pagingData ->
            pagingData.map {
                it.toMovie()
            }
        }
    }
}