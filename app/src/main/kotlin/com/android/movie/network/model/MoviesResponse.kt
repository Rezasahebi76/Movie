package com.android.movie.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    val page: Int,
    val results: List<MovieResponse>,
    val totalPages: Int,
    val totalResults: Int
)