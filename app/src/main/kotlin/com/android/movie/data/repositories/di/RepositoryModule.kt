package com.android.movie.data.repositories.di

import com.android.movie.data.repositories.configuration.ConfigurationRepository
import com.android.movie.data.repositories.configuration.ConfigurationRepositoryImpl
import com.android.movie.data.repositories.movie.MovieRepository
import com.android.movie.data.repositories.movie.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

    @Binds
    fun bindConfigurationRepository(impl: ConfigurationRepositoryImpl): ConfigurationRepository
}