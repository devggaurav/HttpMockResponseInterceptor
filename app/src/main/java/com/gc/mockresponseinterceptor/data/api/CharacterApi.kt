package com.gc.mockresponseinterceptor.data.api

import com.gc.mockResponseInterceptor.MockSuccessResponse
import com.gc.mockresponseinterceptor.data.ApiConstants
import retrofit2.http.GET
import com.gc.mockresponseinterceptor.data.api.model.Character

interface CharacterApi {
    @GET(ApiConstants.END_POINTS)
    @MockSuccessResponse(fileName = "characters_response.json")
    suspend fun getCharacter(): List<Character>
}