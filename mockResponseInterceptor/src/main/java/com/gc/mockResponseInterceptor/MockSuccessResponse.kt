package com.gc.mockResponseInterceptor


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class MockSuccessResponse(
    val fileName: String
)
