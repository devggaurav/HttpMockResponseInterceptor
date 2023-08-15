package com.gc.mockResponseInterceptor

import java.io.InputStreamReader
import java.lang.StringBuilder

object Utils {

    fun readFileResource(fileName: String?): String {
        val inputStream = Utils::class.java.getResourceAsStream(fileName)
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    }
}