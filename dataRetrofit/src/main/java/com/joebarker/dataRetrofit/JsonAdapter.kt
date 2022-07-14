package com.joebarker.dataRetrofit

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joebarker.domain.entities.ErrorEntity
import retrofit2.Response

class JsonAdapter{
    companion object{
        private val gson = Gson()

        fun <T> convertToError(response: Response<T>) : ErrorEntity {
            val type = object : TypeToken<ErrorEntity>() {}.type
            return gson.fromJson(response.errorBody()?.charStream(), type)
        }
    }
}