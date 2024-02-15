package com.android.movie.data.datasource.remote.movie

import com.android.movie.network.apis.MoviesApis
import com.android.movie.network.model.movie.MoviesResponse
import javax.inject.Inject

class MoviesRemoteDataSourceImpl @Inject constructor(
    private val moviesApis: MoviesApis,
) : MoviesRemoteDataSource {

    override suspend fun getUpcomingMovies(page: Int): MoviesResponse =
        moviesApis.getUpcomingMovies(page = page)

}