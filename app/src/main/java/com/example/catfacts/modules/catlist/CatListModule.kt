package com.example.catfacts.modules.catlist

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val catListModule = module {
    viewModel { CatListViewModel(get()) }
}
