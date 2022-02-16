package com.example.catfacts.extensions

import com.example.catfacts.data.api.thecatapi.model.ImageResponse
import com.example.catfacts.data.domain.CatImage

fun CatImage.Companion.fromRemoteValue(remoteValue: ImageResponse): CatImage {
    return CatImage(
        url = remoteValue.url
    )
}
