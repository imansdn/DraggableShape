package com.example.simpledraggableshape.di.modules

import android.content.Context
import androidx.room.Room
import com.example.simpledraggableshape.data.source.local.AppDatabase
import com.example.simpledraggableshape.di.DatabaseModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object DatabaseTestModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .inMemoryDatabaseBuilder(context.applicationContext, AppDatabase::class.java)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .build()
    }
}
