package com.android.movie.data.mapper

import com.android.movie.database.entities.MovieEntity
import com.android.movie.network.model.MovieResponse


fun MovieResponse.toMovieEntity():MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        posterPath = posterPath
    )
}