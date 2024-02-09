package com.android.movie.data.datasource.remote

import com.android.movie.common.dispatcher.Dispatcher
import com.android.movie.common.dispatcher.MovieDispatchers
import com.android.movie.network.model.MoviesResponse
import com.android.movie.network.service.MoviesApis
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRemoteDataSourceImpl @Inject constructor(
    private val moviesApis: MoviesApis,
    @Dispatcher(MovieDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher,
) : MoviesRemoteDataSource {

    override suspend fun getUpcomingMovies(page: Int): MoviesResponse {
        return withContext(ioDispatcher) {
            moviesApis.getUpcomingMovies(page = page)
        }
    }
}