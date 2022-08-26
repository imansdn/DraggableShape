package com.example.simpledraggableshape.domain.usecase

import com.example.simpledraggableshape.data.repository.RectanglesRepository
import com.example.simpledraggableshape.domain.model.Movement
import com.example.simpledraggableshape.domain.model.Rectangle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateRectanglesUseCase @Inject constructor(private val repository: RectanglesRepository) {

    operator fun invoke(rectangles: List<Rectangle>, movement: Movement): Flow<List<Rectangle>> {
        return flow {
            val selectedRect = rectangles.first { it.id == movement.id }
            val newRect = selectedRect.run {
                copy(
                    xCorner = xCorner + movement.x,
                    yCorner = yCorner + movement.y,
                )
            }
            repository.updatePosition(newRect).collect {
                val index = rectangles.indexOfFirst { it.id == movement.id }
                rectangles.toMutableList().let {
                    it[index] = newRect
                    emit(it)
                }
            }
        }

    }

}