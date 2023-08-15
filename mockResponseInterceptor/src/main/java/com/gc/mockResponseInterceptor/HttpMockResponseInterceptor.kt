package com.gc.mockResponseInterceptor

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Invocation
import java.io.FileNotFoundException

class HttpMockResponseInterceptor private constructor(
    private val doMock: () -> Boolean = { true }
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val mockingEnabled = doMock()
        if (mockingEnabled.not()) {
            return chain.proceed(originalRequest)
        }

        originalRequest.tag(Invocation::class.java)
            ?.method()?.getAnnotation(MockSuccessResponse::class.java)?.fileName
            ?: return chain.proceed(originalRequest)

        return generateMockResponse(originalRequest)
    }


    private fun generateMockResponse(request: Request): Response {
        val jsonFileName = request.tag(Invocation::class.java)
            ?.method()?.getAnnotation(MockSuccessResponse::class.java)?.fileName

        val jsonString = kotlin.runCatching {
            Utils.readFileResource(jsonFileName)
        }.onFailure {
            if (it is FileNotFoundException && BuildConfig.DEBUG) {
                error("MockingError: File not found ${request.url()}")
            }
        }.getOrThrow()

        val mockBody = ResponseBody.create(MediaType.parse("application/json"), jsonString)

        return Response.Builder()
            .protocol(Protocol.HTTP_1_1)
            .request(request)
            .code(200)
            .message(mockBody.toString())
            .body(mockBody)
            .build()

    }

    class Builder {

        private var doMock: () -> Boolean = { BuildConfig.DEBUG }

        fun isMockEnabled(doMock: () -> Boolean) = apply {
            this.doMock = doMock
        }

        fun build() = HttpMockResponseInterceptor(doMock)


    }


}