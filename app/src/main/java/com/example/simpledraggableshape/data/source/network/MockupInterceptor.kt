package com.example.simpledraggableshape.data.source.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.*
import retrofit2.Invocation
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockupInterceptor @Inject constructor(@ApplicationContext  val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        lateinit var response: Response
        val request: Request = chain.request()
        val tag: Invocation? = request.tag(Invocation::class.java)
        val mockUp: MockUp? = tag?.method()?.getAnnotation(MockUp::class.java)

        return if (mockUp != null) {
            val mockResponse = context.resources.assets
                .open(mockUp.responsePath)
                .bufferedReader().use { it.readText() }
            response = Response.Builder()
                .code(mockUp.responseCode)
                .message(mockUp.responsePath)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), mockResponse))
                .addHeader("content-type", "application/json")
                .build()
            response
        } else chain.proceed(request)

    }
}