package com.android.movie.network.model.configuration

import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationResponse(
    val images: ImageConfigurationResponse,
)
