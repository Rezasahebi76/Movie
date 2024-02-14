package com.android.movie.data.repositories.movie

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.android.movie.data.datasource.local.movie.MoviesLocalDataSource
import com.android.movie.data.datasource.remote.MoviesRemoteDataSource
import com.android.movie.data.mapper.toMovieEntities
import com.android.movie.database.entities.MovieEntity
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val moviesLocalDataSource: MoviesLocalDataSource,
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    STARTING_PAGE_INDEX
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()

                    lastItem?.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = lastItem != null)
                }
            }
            val response = moviesRemoteDataSource.getUpcomingMovies(page)
            val movies = response.toMovieEntities()
            if (loadType == LoadType.REFRESH) {
                moviesLocalDataSource.refreshAllMovies(movies)
            } else {
                moviesLocalDataSource.insertMovies(movies)
            }
            MediatorResult.Success(endOfPaginationReached = response.totalPages == response.page)
        } catch (exception: Exception) {
            exception.printStackTrace()
            MediatorResult.Error(exception)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}