package com.joebarker.data

import com.joebarker.domain.boundary.data.WeatherData
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

    override suspend fun getWeatherInfoFor(city: String): WeatherInfo? {
        val response = remoteCalls.retrieveWeatherInfo(city).execute()
        if (!response.isSuccessful)
            throw Exception()
        return response.body()?.convert()
    }
}