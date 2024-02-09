package com.android.movie.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    val id: Int,
    val title: String,
    @SerialName("poster_path")
    val posterPath: String?,
)