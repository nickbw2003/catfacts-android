package com.example.catfacts.data.api.thecatapi

import com.example.catfacts.BuildConfig
import com.example.catfacts.data.api.KtorClientFactory
import com.example.catfacts.data.api.thecatapi.model.CategoryResponse
import com.example.catfacts.data.api.thecatapi.model.ImageResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

interface TheCatApi {
    suspend fun imageSearch(): List<ImageResponse>
    suspend fun getCategories(): List<CategoryResponse>
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
        applyApiKeyHeader()
    }

    override suspend fun getCategories(): List<CategoryResponse> = httpClient.request(
        "${apiBaseUrl}/${categoriesEndpoint}"
    ) {
        method = HttpMethod.Get
        applyApiKeyHeader()
    }

    private fun HttpRequestBuilder.applyApiKeyHeader() {
        headers {
            append(apiKeyHeaderName, BuildConfig.THE_CAT_API_KEY)
        }
    }

    companion object {
        private const val apiBaseUrl = "https://api.thecatapi.com/v1"
        private const val imageSearchEndpoint = "images/search"
        private const val categoriesEndpoint = "categories"
        private const val apiKeyHeaderName = "x-api-key"
    }
}
