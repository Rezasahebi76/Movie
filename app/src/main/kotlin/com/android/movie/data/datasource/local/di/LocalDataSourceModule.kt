package com.android.movie.data.datasource.local.di

import com.android.movie.data.datasource.local.MoviesLocalDataSource
import com.android.movie.data.datasource.local.MoviesLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface LocalDataSourceModule {

    @Binds
    fun bindMoviesLocalDataSource(impl: MoviesLocalDataSourceImpl): MoviesLocalDataSource
}