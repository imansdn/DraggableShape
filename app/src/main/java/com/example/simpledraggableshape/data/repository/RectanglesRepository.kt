package com.example.simpledraggableshape.data.repository

import com.example.simpledraggableshape.domain.model.Rectangle
import kotlinx.coroutines.flow.Flow

interface RectanglesRepository {

    fun getRectangles(forceFreshData: Boolean = false): Flow<List<Rectangle>>

    fun updatePosition(rectangle: Rectangle): Flow<Unit>
}