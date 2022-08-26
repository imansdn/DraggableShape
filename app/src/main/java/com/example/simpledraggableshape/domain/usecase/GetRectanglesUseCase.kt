package com.example.simpledraggableshape.domain.usecase

import com.example.simpledraggableshape.data.repository.RectanglesRepository
import com.example.simpledraggableshape.domain.model.Movement
import com.example.simpledraggableshape.domain.model.Rectangle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetRectanglesUseCase @Inject constructor(private val repository: RectanglesRepository) {

    operator fun invoke(): Flow<List<Rectangle>> {
        return repository.getRectangles().map { list -> list.sortedBy { it.lastModified } }
    }

}