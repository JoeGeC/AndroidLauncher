package com.joebarker.data

import com.joebarker.domain.boundary.data.WeatherData
import com.joebarker.domain.entities.Either
import com.joebarker.domain.entities.ErrorEntity
import com.joebarker.domain.entities.ErrorMessage
import com.joebarker.domain.entities.WeatherInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class WeatherDataImpl(
    private val remoteCalls: WeatherRemoteCalls = retrofit.create(WeatherRemoteCalls::class.java)
): WeatherData{

    companion object {
        private const val BASE_URL: String = "https://weather.bfsah.com/"

        internal val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override suspend fun getWeatherInfoFor(city: String): Either<WeatherInfo?, ErrorEntity> {
        return try{
            val result = remoteCalls.retrieveWeatherInfo(city).execute()
            return if (result.isSuccessful) {
                Either.Success(result.body()?.convert())
            } else {
                val error = JsonAdapter.convertToError(result)
                Either.Failure(error)
            }
        } catch(exception: Exception){
            val error = ErrorEntity(ErrorMessage(exception.localizedMessage ?: "Error"))
            Either.Failure(error)
        }
    }
}