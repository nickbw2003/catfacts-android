package com.example.catfacts.modules.catfact

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val catFactModule = module {
    viewModel { CatFactViewModel(get(), get(), get(), get()) }
}
