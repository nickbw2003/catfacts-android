package com.example.catfacts.extensions

import com.example.catfacts.data.api.thecatapi.model.CategoryResponse
import com.example.catfacts.data.domain.CatCategory

fun CatCategory.Companion.fromRemoteValue(remoteValue: CategoryResponse): CatCategory {
    return CatCategory(
        id = remoteValue.id,
        name = remoteValue.name
    )
}
