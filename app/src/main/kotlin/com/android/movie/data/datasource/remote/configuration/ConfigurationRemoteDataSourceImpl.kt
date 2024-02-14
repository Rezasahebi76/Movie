package com.android.movie.data.datasource.remote.configuration

import com.android.movie.common.dispatcher.Dispatcher
import com.android.movie.common.dispatcher.MovieDispatchers
import com.android.movie.network.apis.ConfigurationApis
import com.android.movie.network.model.configuration.ImageConfigurationResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConfigurationRemoteDataSourceImpl @Inject constructor(
    private val configurationApis: ConfigurationApis,
    @Dispatcher(MovieDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher,
) : ConfigurationRemoteDataSource {

    override suspend fun getImageConfiguration(): ImageConfigurationResponse =
        withContext(ioDispatcher) {
            configurationApis.getConfiguration().images
        }

}