package com.android.movie.data.repositories.configuration

import kotlinx.coroutines.flow.Flow

interface ConfigurationRepository {

    val imageBaseUrl: Flow<String?>

    suspend fun fetchImageBaseUrl()
}


