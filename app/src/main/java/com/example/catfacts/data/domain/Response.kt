package com.example.catfacts.data.domain

sealed class Response<T> {
    data class Success<T>(val data: T) : Response<T>()
    data class Failure<T>(val reason: FailureReason = FailureReason.GENERIC) : Response<T>()

    enum class FailureReason {
        // in a real world app this would contain more specific enum values
        GENERIC
    }
}