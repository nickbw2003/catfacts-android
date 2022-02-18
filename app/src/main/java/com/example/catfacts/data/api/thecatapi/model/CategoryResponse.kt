package com.example.catfacts.data.api.thecatapi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String
)
