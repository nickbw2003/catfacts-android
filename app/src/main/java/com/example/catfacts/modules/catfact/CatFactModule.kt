package com.example.catfacts.modules.catfact

import com.example.catfacts.modules.catfact.util.TopBarActionHandler
import com.example.catfacts.modules.catfact.util.TopBarActionHandlerImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val catFactModule = module {
    viewModel { CatFactViewModel(get(), get(), get()) }
    single<TopBarActionHandler> { TopBarActionHandlerImpl(get(), get()) }
}
