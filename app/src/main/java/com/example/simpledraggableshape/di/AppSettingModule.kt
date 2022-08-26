package com.example.simpledraggableshape.di

import com.example.simpledraggableshape.data.source.local.AppSettingImpl
import com.example.simpledraggableshape.domain.setting.AppSetting
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppSettingModule {

    @Binds
    @Singleton
    abstract fun provideAppSetting(appSettingImpl: AppSettingImpl): AppSetting

}