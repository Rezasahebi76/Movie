package com.android.movie.data.datasource.local.preferences

import kotlinx.coroutines.flow.Flow

interface PreferencesLocalDataSource {
    val lastPage: Flow<Int>

    val ImageBaseUrl: Flow<String?>

    suspend fun updateLastPage(lastPage: Int)

    suspend fun updateImageBaseUrl(baseImageUrl: String)
}