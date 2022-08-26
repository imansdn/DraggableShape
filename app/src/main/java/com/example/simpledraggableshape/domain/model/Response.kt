package com.example.simpledraggableshape.domain.model

sealed class Response<out T> {
    object None: Response<Nothing>()
    object Loading: Response<Nothing>()

    data class Success<out T>(
        val data: T?
    ): Response<T>()

    data class Failure(
        val e: Throwable?
    ): Response<Nothing>()
}