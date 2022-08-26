package com.example.simpledraggableshape.data.source

import com.example.simpledraggableshape.data.repository.RectanglesRepository
import com.example.simpledraggableshape.domain.model.Rectangle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FakeRectangleRepository @Inject constructor(
    val dispatcher: CoroutineDispatcher
) : RectanglesRepository {
    val rectangles = arrayListOf<Rectangle>()
    var forceCrashing = false

    override fun getRectangles(forceFreshData: Boolean): Flow<List<Rectangle>> {
        return flow {
            if (forceCrashing)
                throw Exception()
            emit(rectangles.toList())
        }.flowOn(dispatcher)
    }

    override fun updatePosition(rectangle: Rectangle): Flow<Unit> {
        return flow {
            if (forceCrashing) {
                throw Exception()
            }
            rectangles[rectangles.indexOfFirst { it.id == rectangle.id }] = rectangle
            emit(Unit)
        }.flowOn(dispatcher)
    }
}