package com.android.movie.data.datasource.remote.configuration

import com.android.movie.network.model.configuration.ImageConfigurationResponse

interface ConfigurationRemoteDataSource {
    suspend fun getImageConfiguration(): ImageConfigurationResponse
}