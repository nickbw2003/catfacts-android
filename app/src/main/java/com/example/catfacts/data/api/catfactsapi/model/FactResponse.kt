package com.example.catfacts.data.api.catfactsapi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FactResponse(
    @SerialName("text") val text: String
)
