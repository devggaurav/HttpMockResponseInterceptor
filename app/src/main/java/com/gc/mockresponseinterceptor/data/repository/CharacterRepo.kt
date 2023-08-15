package com.gc.mockresponseinterceptor.data.repository

import com.gc.mockresponseinterceptor.data.api.CharacterApi
import javax.inject.Inject
import com.gc.mockresponseinterceptor.data.api.model.Character

class CharacterRepo @Inject constructor(
    private val characterApi: CharacterApi
) {
    suspend fun getCharacters(): List<Character> {
        return characterApi.getCharacter()
    }
}