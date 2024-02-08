package com.android.movie.network.service

import com.android.movie.network.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApis {
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int
    ): MoviesResponse
}