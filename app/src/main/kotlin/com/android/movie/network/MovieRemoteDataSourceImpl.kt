package com.android.movie.network

import com.android.movie.network.model.MoviesResponse
import com.android.movie.network.service.MoviesApis
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val moviesApis: MoviesApis
) : MoviesRemoteDataSource {

    override suspend fun getUpcomingMovies(page: Int): MoviesResponse {
        return moviesApis.getUpcomingMovies(page = page)
    }
}