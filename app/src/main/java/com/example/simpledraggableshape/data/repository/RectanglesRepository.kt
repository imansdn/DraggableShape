package com.example.simpledraggableshape.data.repository

import com.example.simpledraggableshape.data.source.local.AppDatabase
import com.example.simpledraggableshape.data.source.local.entity.RectangleEntity
import com.example.simpledraggableshape.data.source.local.entity.toModel
import com.example.simpledraggableshape.data.source.network.RetrofitService
import com.example.simpledraggableshape.data.source.network.dto.toEntity
import com.example.simpledraggableshape.di.qualifier.IoDispatcher
import com.example.simpledraggableshape.domain.model.Movement
import com.example.simpledraggableshape.domain.model.Rectangle
import com.example.simpledraggableshape.domain.setting.AppSetting
import com.example.simpledraggableshape.util.DateUtil
import com.example.simpledraggableshape.util.roundTo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RectanglesRepository @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val retrofitService: RetrofitService,
    private val appDatabase: AppDatabase,
    private val appSetting: AppSetting
) {

    fun getRectangles(needGetFreshData: Boolean = needGetFreshRectangles()): Flow<List<Rectangle>> {
        return flow {
            val local = appDatabase.rectangleDao().getRectangles()
            val data = if (local.isNullOrEmpty() || needGetFreshData) {
                val response = retrofitService.getRectangleRequest().rectangles
                val entities = response.map { it.toEntity() }
                appDatabase.rectangleDao().insertAll(rectangles = entities)
                entities
            } else {
                appDatabase.rectangleDao().getRectangles()
            }
            emit(data?.map { it.toModel() } ?: listOf())
        }.flowOn(dispatcher)
    }

    private fun needGetFreshRectangles(): Boolean {
        return DateUtil.isMoreThanWeek(appSetting.getLastVisit())
    }

    fun updatePosition(rectangle: Rectangle): Flow<Unit> {
        return flow<Unit> {
            appSetting.saveLastVisit()
            emit(
                appDatabase.rectangleDao()
                    .updatePosition(
                        id = rectangle.id,
                        x = rectangle.xCorner.toDouble(),
                        y = rectangle.yCorner.toDouble(),
                        lastModified = rectangle.lastModified
                    )
            )
        }.flowOn(dispatcher)
    }
}