package com.example.catfacts

import android.app.Application
import com.example.catfacts.modules.catfact.catFactModule
import com.example.catfacts.modules.catlist.catListModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CatFactsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CatFactsApplication)
            modules(
                catFactsAppModule,
                catFactModule,
                catListModule
            )
        }
    }
}