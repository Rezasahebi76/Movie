package com.android.movie.network

import com.android.movie.network.model.MoviesResponse

interface MoviesRemoteDataSource {
    suspend fun getMovies(page: Int): MoviesResponse
}