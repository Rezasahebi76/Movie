package com.android.movie.data.datasource.local.di

import com.android.movie.data.datasource.local.movie.MoviesLocalDataSource
import com.android.movie.data.datasource.local.movie.MoviesLocalDataSourceImpl
import com.android.movie.data.datasource.local.preferences.PreferencesLocalDataSource
import com.android.movie.data.datasource.local.preferences.PreferencesLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface LocalDataSourceModule {

    @Binds
    fun bindMoviesLocalDataSource(impl: MoviesLocalDataSourceImpl): MoviesLocalDataSource

    @Binds
    fun bindPreferencesLocalDataSource(impl: PreferencesLocalDataSourceImpl): PreferencesLocalDataSource
}