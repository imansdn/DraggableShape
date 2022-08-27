package com.example.simpledraggableshape.di.modules

import com.example.simpledraggableshape.data.source.local.AppSettingTestImpl
import com.example.simpledraggableshape.di.AppSettingModule
import com.example.simpledraggableshape.domain.setting.AppSetting
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppSettingModule::class]
)
abstract class AppSettingTestModule {

    @Binds
    abstract fun provideAppSettingManager(
        appSettingManagerImpl: AppSettingTestImpl
    ): AppSetting

}