package com.example.simpledraggableshape.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.simpledraggableshape.data.source.local.dao.RectangleDao
import com.example.simpledraggableshape.data.source.local.entity.RectangleEntity

internal const val DATABASE_NAME = "rect-database"
private const val DATABASE_VERSION = 2

@Database(
    entities = [RectangleEntity::class],
    version = DATABASE_VERSION, exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rectangleDao(): RectangleDao
}