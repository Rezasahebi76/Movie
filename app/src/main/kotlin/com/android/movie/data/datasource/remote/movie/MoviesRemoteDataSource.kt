package com.android.movie.data.datasource.remote.movie

import com.android.movie.network.model.movie.MoviesResponse

interface MoviesRemoteDataSource {
    suspend fun getUpcomingMovies(page: Int): MoviesResponse
}