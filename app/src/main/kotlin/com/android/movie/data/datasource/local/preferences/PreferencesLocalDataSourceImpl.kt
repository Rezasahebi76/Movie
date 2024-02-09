package com.android.movie.data.datasource.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesLocalDataSourceImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    PreferencesLocalDataSource {

    private val nextPageKey = intPreferencesKey("last_page")

   override val lastPage: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[nextPageKey] ?: 1
        }

   override suspend fun updateLastPage(lastPage: Int) {
        dataStore.edit {
            it[nextPageKey] = lastPage
        }
    }
}