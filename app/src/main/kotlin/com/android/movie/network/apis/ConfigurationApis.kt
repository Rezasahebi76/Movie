package com.android.movie.network.apis

import com.android.movie.network.model.configuration.ConfigurationResponse
import retrofit2.http.GET

interface ConfigurationApis {
    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationResponse
}