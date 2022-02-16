package com.example.catfacts.data.api

import com.example.catfacts.data.domain.Response
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

interface RemoteImageHandler {
    suspend fun downloadImage(url: String): Response<ByteArray>
}

class RemoteImageHandlerImpl(
    private val ktorClientFactory: KtorClientFactory
) : RemoteImageHandler {

    private val httpClient: HttpClient by lazy {
        ktorClientFactory.createClient()
    }

    override suspend fun downloadImage(url: String): Response<ByteArray> {
        return try {
            val imageByteArray: ByteArray = httpClient.request(url) {
                method = HttpMethod.Get
            }
            Response.Success(imageByteArray)
        } catch (t: Throwable) {
            Response.Failure(reason = Response.FailureReason.GENERIC)
        }
    }
}
