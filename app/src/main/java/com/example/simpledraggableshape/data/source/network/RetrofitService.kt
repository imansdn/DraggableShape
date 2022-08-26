package com.example.simpledraggableshape.data.source.network

import com.example.simpledraggableshape.data.source.network.dto.GetRectangleDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @MockUp(200, "mock/get_rectangles.json")
    @GET("words")
    suspend fun getRectangleRequest(): GetRectangleDto


}



