package com.android.movie.network.model.configuration

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageConfigurationResponse(
    @SerialName("secure_base_url")
    val secureBaseUrl: String,
    @SerialName("poster_sizes")
    val posterSizes: List<String>
)