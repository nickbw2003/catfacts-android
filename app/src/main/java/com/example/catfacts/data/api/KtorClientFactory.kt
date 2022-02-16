package com.example.catfacts.data.api

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.json.Json

interface KtorClientFactory {
    fun createClient(): HttpClient
}

class KtorClientFactoryImpl : KtorClientFactory {

    private val json: Json by lazy {
        Json {
            ignoreUnknownKeys = true
        }
    }

    override fun createClient(): HttpClient {
        return HttpClient(OkHttp) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(json)
            }
        }
    }
}
