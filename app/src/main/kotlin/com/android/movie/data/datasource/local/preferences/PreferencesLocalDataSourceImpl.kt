package com.android.movie.data.datasource.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesLocalDataSourceImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    PreferencesLocalDataSource {

    private val imageBaseUrlKey  = stringPreferencesKey("base_image_url")

    override val imageBaseUrl: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[imageBaseUrlKey]
        }
    override suspend fun updateImageBaseUrl(baseImageUrl: String) {
        dataStore.edit {
            it[imageBaseUrlKey] = baseImageUrl
        }
    }

}