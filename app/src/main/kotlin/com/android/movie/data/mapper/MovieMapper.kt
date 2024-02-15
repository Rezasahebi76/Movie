package com.android.movie.data.mapper

import com.android.movie.database.entities.MovieEntity
import com.android.movie.models.Movie
import com.android.movie.network.model.movie.MoviesResponse

fun MoviesResponse.toMovieEntities(): List<MovieEntity> {
    val nextPage = if (page == totalPages) null else page + 1
    return results.map {
        MovieEntity(
            remoteId = it.id,
            title = it.title,
            posterPath = it.posterPath,
            nextPage = nextPage
        )
    }
}

fun MovieEntity.toMovie(): Movie = Movie(
    id = id,
    title = title,
    posterPath = posterPath
)
