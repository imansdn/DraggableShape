package com.example.simpledraggableshape.data.source.network.dto


import com.example.simpledraggableshape.data.source.local.entity.RectangleEntity
import com.example.simpledraggableshape.util.randomColor
import com.google.gson.annotations.SerializedName

data class GetRectangleDto(
    @SerializedName("rectangles")
    val rectangles: List<RectangleDto>
) {
    data class RectangleDto(
        @SerializedName("id")
        val id: Int, // 0.2
        @SerializedName("width")
        val width: Float,
        @SerializedName("height")
        val height: Float,
        @SerializedName("x")
        val x: Double, // 0.5
        @SerializedName("y")
        val y: Double // 0.5
    )
}

fun GetRectangleDto.RectangleDto.toEntity(): RectangleEntity =
    RectangleEntity(id = id, x = x, y = y, width = width, height = height, color = randomColor())