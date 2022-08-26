package com.example.simpledraggableshape.data.source.network

import com.example.simpledraggableshape.di.qualifier.BaseUrl
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitBuilderImpl @Inject constructor(
    val okHttpClient: OkHttpClient,
    @BaseUrl val url: String,
    val gson: Gson
) {
    fun makeRetrofitService(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}