package com.android.movie.data.repositories.configuration

import com.android.movie.data.datasource.local.preferences.PreferencesLocalDataSource
import com.android.movie.data.datasource.remote.configuration.ConfigurationRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConfigurationRepositoryImpl @Inject constructor(
    private val localDataSource: PreferencesLocalDataSource,
    private val remoteDataSource: ConfigurationRemoteDataSource
) : ConfigurationRepository {

    override val imageBaseUrl: Flow<String?> = localDataSource.ImageBaseUrl

    override suspend fun fetchImageBaseUrl() {
        val response = remoteDataSource.getImageConfiguration()
        localDataSource.updateImageBaseUrl(response.secureBaseUrl + response.posterSizes.last())
    }

}