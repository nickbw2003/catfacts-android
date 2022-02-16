package com.example.catfacts.data.api.thecatapi

import com.example.catfacts.data.api.KtorClientFactory
import com.example.catfacts.data.api.thecatapi.model.ImageResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

interface TheCatApi {
    suspend fun imageSearch(): List<ImageResponse>
}

class TheCatApiImpl(
    private val ktorClientFactory: KtorClientFactory
) : TheCatApi {

    private val httpClient: HttpClient by lazy {
        ktorClientFactory.createClient()
    }

    override suspend fun imageSearch(): List<ImageResponse> = httpClient.request(
        "${apiBaseUrl}/${imageSearchEndpoint}"
    ) {
        method = HttpMethod.Get
    }

    companion object {
        private const val apiBaseUrl = "https://api.thecatapi.com/v1"
        private const val imageSearchEndpoint = "images/search"
    }
}
