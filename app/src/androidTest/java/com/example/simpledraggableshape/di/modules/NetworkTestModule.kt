package com.example.simpledraggableshape.di.modules

import com.example.simpledraggableshape.data.source.network.RetrofitService
import com.example.simpledraggableshape.data.source.remote.FakeRetrofitService
import com.example.simpledraggableshape.di.NetworkModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
object NetworkTestModule {
    @Singleton
    @Provides
    fun provideRetrofit(): RetrofitService {
        return FakeRetrofitService()
    }
}