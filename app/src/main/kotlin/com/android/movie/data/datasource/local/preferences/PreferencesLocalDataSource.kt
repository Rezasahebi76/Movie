package com.android.movie.data.datasource.local.preferences

import kotlinx.coroutines.flow.Flow

interface PreferencesLocalDataSource {
    val imageBaseUrl: Flow<String?>
    suspend fun updateImageBaseUrl(baseImageUrl: String)
}