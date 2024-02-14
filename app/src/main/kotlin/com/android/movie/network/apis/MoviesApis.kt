package com.android.movie.network.apis

import com.android.movie.network.model.movie.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApis {
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int
    ): MoviesResponse
}