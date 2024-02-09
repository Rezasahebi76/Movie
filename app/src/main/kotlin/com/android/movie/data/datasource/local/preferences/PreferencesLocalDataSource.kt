package com.android.movie.data.datasource.local.preferences

import kotlinx.coroutines.flow.Flow

interface PreferencesLocalDataSource {
    val lastPage: Flow<Int>
    suspend fun updateLastPage(lastPage: Int)
}