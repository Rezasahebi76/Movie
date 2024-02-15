package com.android.movie.data.datasource.remote.configuration

import com.android.movie.network.apis.ConfigurationApis
import com.android.movie.network.model.configuration.ImageConfigurationResponse
import javax.inject.Inject

class ConfigurationRemoteDataSourceImpl @Inject constructor(
    private val configurationApis: ConfigurationApis
) : ConfigurationRemoteDataSource {

    override suspend fun getImageConfiguration(): ImageConfigurationResponse =
        configurationApis.getConfiguration().images


}