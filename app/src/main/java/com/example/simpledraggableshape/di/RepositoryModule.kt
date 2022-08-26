package com.example.simpledraggableshape.di

import com.example.simpledraggableshape.data.repository.RectanglesRepository
import com.example.simpledraggableshape.data.repository.RectanglesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun provideRectangleRepository(
        rectangleRepository: RectanglesRepositoryImpl
    ): RectanglesRepository
}