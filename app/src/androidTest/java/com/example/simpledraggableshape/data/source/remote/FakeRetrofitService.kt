package com.example.simpledraggableshape.data.source.remote

import com.example.simpledraggableshape.data.source.network.RetrofitService
import com.example.simpledraggableshape.data.source.network.dto.GetRectangleDto

class FakeRetrofitService : RetrofitService {
    var response = GetRectangleDto(rectangles = listOf())


    override suspend fun getRectangleRequest(): GetRectangleDto {
        return response
    }
}