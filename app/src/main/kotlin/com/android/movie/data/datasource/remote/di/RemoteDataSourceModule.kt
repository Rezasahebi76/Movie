package com.android.movie.data.datasource.remote.di

import com.android.movie.data.datasource.remote.movie.MoviesRemoteDataSource
import com.android.movie.data.datasource.remote.movie.MoviesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RemoteDataSourceModule {

    @Binds
    fun bindMoviesRemoteDataSource(impl: MoviesRemoteDataSourceImpl): MoviesRemoteDataSource
}