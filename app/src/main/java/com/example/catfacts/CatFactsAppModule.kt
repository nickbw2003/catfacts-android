package com.example.catfacts

import com.example.catfacts.data.LocalImageHandler
import com.example.catfacts.data.LocalImageHandlerImpl
import com.example.catfacts.data.RemoteImageHandler
import com.example.catfacts.data.RemoteImageHandlerImpl
import com.example.catfacts.data.api.*
import com.example.catfacts.data.api.catfactsapi.CatFactApi
import com.example.catfacts.data.api.catfactsapi.CatFactApiImpl
import com.example.catfacts.data.api.thecatapi.TheCatApi
import com.example.catfacts.data.api.thecatapi.TheCatApiImpl
import com.example.catfacts.data.catfact.CatFactRemoteDataStore
import com.example.catfacts.data.catfact.CatFactRemoteDataStoreImpl
import com.example.catfacts.data.catfact.CatFactRepository
import com.example.catfacts.data.catfact.CatFactRepositoryImpl
import com.example.catfacts.data.catimage.CatImageRemoteDataStore
import com.example.catfacts.data.catimage.CatImageRemoteDataStoreImpl
import com.example.catfacts.data.catimage.CatImageRepository
import com.example.catfacts.data.catimage.CatImageRepositoryImpl
import org.koin.dsl.module

val catFactsAppModule = module {
    single<KtorClientFactory> { KtorClientFactoryImpl() }
    single<TheCatApi> { TheCatApiImpl(get()) }
    single<CatFactApi> { CatFactApiImpl(get()) }
    single<RemoteImageHandler> { RemoteImageHandlerImpl(get()) }
    single<LocalImageHandler> { LocalImageHandlerImpl(get()) }
    single<CatImageRemoteDataStore> { CatImageRemoteDataStoreImpl(get()) }
    single<CatFactRemoteDataStore> { CatFactRemoteDataStoreImpl(get()) }
    single<CatImageRepository> { CatImageRepositoryImpl(get()) }
    single<CatFactRepository> { CatFactRepositoryImpl(get()) }
}
