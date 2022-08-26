package com.example.simpledraggableshape.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.simpledraggableshape.domain.model.Rectangle

@Entity
data class RectangleEntity(
    @PrimaryKey val id: Int,
    val x: Double,
    val y: Double,
    val width: Float,
    val height: Float,
    val color: Int,
    val lastModified: Long = System.currentTimeMillis()
)

fun RectangleEntity.toModel(): Rectangle =
    Rectangle(id, x.toFloat(), y.toFloat(), width, height, color, lastModified)