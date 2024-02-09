package com.android.movie.data.datasource.remote

import com.android.movie.network.model.MoviesResponse

interface MoviesRemoteDataSource {
    suspend fun getUpcomingMovies(page: Int): MoviesResponse
}