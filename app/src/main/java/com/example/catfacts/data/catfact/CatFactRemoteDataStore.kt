package com.example.catfacts.data.catfact

import com.example.catfacts.data.api.catfactsapi.CatFactApi
import com.example.catfacts.data.domain.CatFact
import com.example.catfacts.data.domain.Response
import com.example.catfacts.extensions.fromRemoteValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface CatFactRemoteDataStore {
    fun getRandomCatFact(): Flow<Response<CatFact>>
}

class CatFactRemoteDataStoreImpl(
    private val catFactApi: CatFactApi
) : CatFactRemoteDataStore {

    override fun getRandomCatFact(): Flow<Response<CatFact>> = flow {
        val response = try {
            val fact = catFactApi.factsRandom()
            Response.Success(CatFact.fromRemoteValue(fact))
        } catch (t: Throwable) {
            Response.Failure(reason = Response.FailureReason.GENERIC)
        }
        emit(response)
    }
}
