package com.example.simpledraggableshape.domain.model

data class Rectangle(
    val id: Int,
    var xCorner: Float = 0f,
    var yCorner: Float = 0f,
    val width: Float = 0f,
    val height: Float = 0f,
    val color: Int,
    val lastModified: Long
)