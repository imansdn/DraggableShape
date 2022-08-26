package com.example.simpledraggableshape.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simpledraggableshape.data.source.local.entity.RectangleEntity

@Dao
interface RectangleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rectangles: List<RectangleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rectangleEntity: RectangleEntity)

    @Query("UPDATE RectangleEntity SET x = :x, y = :y, lastModified = :lastModified WHERE id = :id")
    suspend fun updatePosition(id: Int, x: Double, y: Double, lastModified: Long)

    @Query("SELECT * FROM RectangleEntity")
    suspend fun getRectangles(): List<RectangleEntity>?

}