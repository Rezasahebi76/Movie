package com.android.movie.data.repositories.movie

import androidx.paging.PagingData
import com.android.movie.models.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMoviePagingData(): Flow<PagingData<Movie>>
}


