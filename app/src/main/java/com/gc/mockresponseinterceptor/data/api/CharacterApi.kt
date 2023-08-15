package com.gc.mockresponseinterceptor.data.api

import com.gc.mockresponseinterceptor.data.ApiConstants
import retrofit2.http.GET

interface CharacterApi {
    @GET(ApiConstants.END_POINTS)
    suspend fun getCharacter(): List<Character>
}