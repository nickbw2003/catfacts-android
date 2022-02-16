package com.example.catfacts.data.api.thecatapi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    @SerialName("url") val url: String
)
