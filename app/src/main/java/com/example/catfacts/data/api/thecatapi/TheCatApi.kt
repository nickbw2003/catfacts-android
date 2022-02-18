package com.example.catfacts.data.api.thecatapi

import com.example.catfacts.BuildConfig
import com.example.catfacts.data.api.KtorClientFactory
import com.example.catfacts.data.api.thecatapi.model.CategoryResponse
import com.example.catfacts.data.api.thecatapi.model.ImageResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

interface TheCatApi {
    suspend fun imageSearch(
        categoryId: Long? = null,
        limit: Int? = 0
    ): List<ImageResponse>

    suspend fun getCategories(): List<CategoryResponse>
}

class TheCatApiImpl(
    private val ktorClientFactory: KtorClientFactory
) : TheCatApi {

    private val httpClient: HttpClient by lazy {
        ktorClientFactory.createClient()
    }

    override suspend fun imageSearch(
        categoryId: Long?,
        limit: Int?
    ): List<ImageResponse> = httpClient.request(
        buildImageSearchUrl(categoryId, limit)
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

    private fun buildImageSearchUrl(
        categoryId: Long?,
        limit: Int?
    ): String {
        return StringBuilder().apply {
            append(apiBaseUrl)
            append("/")
            append(imageSearchEndpoint)
            append("?")
            append(mimeTypesQueryParamName)
            append("=")
            append(supportedMimeType)

            categoryId?.let {
                append("&")
                append(categoryIdsQueryParamName)
                append("=")
                append(it)
            }
            limit?.let {
                append("&")
                append(limitQueryParamName)
                append("=")
                append(it)
            }
        }.toString()
    }

    companion object {
        private const val apiBaseUrl = "https://api.thecatapi.com/v1"
        private const val imageSearchEndpoint = "images/search"
        private const val categoriesEndpoint = "categories"
        private const val apiKeyHeaderName = "x-api-key"
        private const val categoryIdsQueryParamName = "categoryId"
        private const val limitQueryParamName = "limit"
        private const val mimeTypesQueryParamName = "mime_types"
        private const val supportedMimeType = "jpg"
    }
}
