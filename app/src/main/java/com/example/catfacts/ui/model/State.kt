package com.example.catfacts.ui.model

data class State<T>(
    val data: T? = null,
    val loadingState: LoadingState
)
