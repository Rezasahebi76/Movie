package com.android.movie.data.datasource.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject

class PreferencesLocalDataSourceImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    PreferencesLocalDataSource {

}