package com.example.catfacts.data.catimage

import com.example.catfacts.data.domain.CatCategory
import com.example.catfacts.data.domain.CatImage
import com.example.catfacts.data.domain.Response
import kotlinx.coroutines.flow.Flow

interface CatImageRepository {
    fun getRandomCatImage(): Flow<Response<CatImage>>
    fun getImageCategories(): Flow<Response<List<CatCategory>>>
}

class CatImageRepositoryImpl(
    private val catImageRemoteDataStore: CatImageRemoteDataStore
) : CatImageRepository {

    override fun getRandomCatImage(): Flow<Response<CatImage>> {
        return catImageRemoteDataStore.getRandomCatImage()
    }

    override fun getImageCategories(): Flow<Response<List<CatCategory>>> {
        return catImageRemoteDataStore.getImageCategories()
    }
}
