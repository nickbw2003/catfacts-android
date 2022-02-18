package com.example.catfacts.data.catimage

import com.example.catfacts.data.domain.CatCategory
import com.example.catfacts.data.domain.CatImage
import com.example.catfacts.data.domain.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface CatImageRepository {
    fun getRandomCatImage(): Flow<Response<CatImage>>
    fun getImageCategories(): Flow<Response<List<CatCategory>>>
    fun getCatImages(
        categoryId: Long? = null,
        limit: Int? = 0
    ): Flow<Response<List<CatImage>>>
}

class CatImageRepositoryImpl(
    private val catImageRemoteDataStore: CatImageRemoteDataStore
) : CatImageRepository {

    override fun getRandomCatImage(): Flow<Response<CatImage>> {
        return catImageRemoteDataStore.getCatImages().map { imagesResponse ->
            if (imagesResponse is Response.Success && imagesResponse.data.isNotEmpty()) {
                Response.Success(imagesResponse.data.first())
            } else {
                Response.Failure(reason = Response.FailureReason.GENERIC)
            }
        }
    }

    override fun getImageCategories(): Flow<Response<List<CatCategory>>> {
        return catImageRemoteDataStore.getImageCategories()
    }

    override fun getCatImages(
        categoryId: Long?,
        limit: Int?
    ): Flow<Response<List<CatImage>>> {
        return catImageRemoteDataStore.getCatImages(categoryId, limit)
    }
}
