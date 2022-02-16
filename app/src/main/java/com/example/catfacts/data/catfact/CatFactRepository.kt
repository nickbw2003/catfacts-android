package com.example.catfacts.data.catfact

import com.example.catfacts.data.domain.CatFact
import com.example.catfacts.data.domain.Response
import kotlinx.coroutines.flow.Flow

interface CatFactRepository {
    fun getRandomCatFact(): Flow<Response<CatFact>>
}

class CatFactRepositoryImpl(
    private val catFactRemoteDataStore: CatFactRemoteDataStore
) : CatFactRepository {

    override fun getRandomCatFact(): Flow<Response<CatFact>> {
        return catFactRemoteDataStore.getRandomCatFact()
    }
}
