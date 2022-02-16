package com.example.catfacts.data.api.catfactsapi

import com.example.catfacts.data.api.KtorClientFactory
import com.example.catfacts.data.api.catfactsapi.model.FactResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

interface CatFactApi {
    suspend fun factsRandom(
        animalType: String = defaultAnimalType,
        amount: Int = defaultAmount
    ): FactResponse

    companion object {
        private const val defaultAnimalType = "cat"
        private const val defaultAmount = 1
    }
}

class CatFactApiImpl(
    private val ktorClientFactory: KtorClientFactory
) : CatFactApi {

    private val httpClient: HttpClient by lazy {
        ktorClientFactory.createClient()
    }

    override suspend fun factsRandom(
        animalType: String,
        amount: Int
    ): FactResponse = httpClient.request(
        "${apiBaseUrl}/${factsRandomEndpoint}?$animalTypeParam=$animalType&$amountParam=$amount"
    ) {
        method = HttpMethod.Get
    }

    companion object {
        private const val apiBaseUrl = "https://cat-fact.herokuapp.com"
        private const val factsRandomEndpoint = "facts/random"
        private const val animalTypeParam = "animal_type"
        private const val amountParam = "amount"
    }
}
