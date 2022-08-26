package com.example.simpledraggableshape.data.source.network

import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OkHttpClientBuilder @Inject constructor(
    private val mockInterceptor: MockupInterceptor
) {

    fun build(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(mockInterceptor)
        return httpClient.build()
    }

}