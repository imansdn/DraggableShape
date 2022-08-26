package com.example.simpledraggableshape.di

import com.example.simpledraggableshape.data.source.network.MockupInterceptor
import com.example.simpledraggableshape.data.source.network.OkHttpClientBuilder
import com.example.simpledraggableshape.data.source.network.RetrofitBuilderImpl
import com.example.simpledraggableshape.data.source.network.RetrofitService
import com.example.simpledraggableshape.di.qualifier.BaseUrl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
        @BaseUrl url: String
    ): RetrofitService {
        return RetrofitBuilderImpl(
            url = url,
            okHttpClient = okHttpClient,
            gson = gson
        ).makeRetrofitService()
            .create(RetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        mockInterceptor: MockupInterceptor,
    ): OkHttpClient {
        return OkHttpClientBuilder(mockInterceptor).build()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().setExclusionStrategies().create()
    }


    @Singleton
    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = "https://BASE_URL"


}