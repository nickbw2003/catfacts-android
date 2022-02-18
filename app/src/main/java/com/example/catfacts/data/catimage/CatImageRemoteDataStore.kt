package com.example.catfacts.data.catimage

import com.example.catfacts.data.api.thecatapi.TheCatApi
import com.example.catfacts.data.domain.CatCategory
import com.example.catfacts.data.domain.CatImage
import com.example.catfacts.data.domain.Response
import com.example.catfacts.extensions.fromRemoteValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface CatImageRemoteDataStore {
    fun getCatImages(
        categoryId: Long? = null,
        limit: Int? = 0
    ): Flow<Response<List<CatImage>>>

    fun getImageCategories(): Flow<Response<List<CatCategory>>>
}

class CatImageRemoteDataStoreImpl(
    private val theCatApi: TheCatApi
) : CatImageRemoteDataStore {

    override fun getCatImages(
        categoryId: Long?,
        limit: Int?
    ): Flow<Response<List<CatImage>>> = flow {
        val response = try {
            val images = theCatApi.imageSearch(categoryId, limit).map { remoteImage ->
                CatImage.fromRemoteValue(remoteImage)
            }
            Response.Success(images)
        } catch (t: Throwable) {
            Response.Failure(reason = Response.FailureReason.GENERIC)
        }
        emit(response)
    }

    override fun getImageCategories(): Flow<Response<List<CatCategory>>> = flow {
        val response = try {
            val categories = theCatApi.getCategories().map { remoteCategory ->
                CatCategory.fromRemoteValue(remoteCategory)
            }
            Response.Success(categories)
        } catch (t: Throwable) {
            Response.Failure(reason = Response.FailureReason.GENERIC)
        }
        emit(response)
    }
}
