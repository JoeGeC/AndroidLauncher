package com.joebarker.domain

import com.joebarker.domain.boundary.data.WeatherData
import com.joebarker.domain.entities.WeatherInfo
import com.joebarker.domain.usecases.WeatherUseCaseImpl
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class WeatherUseCaseShould {

    @Test
    fun returnWeatherInfoFromData() {
        val weatherInfo = WeatherInfo(
            "Beijing",
            "China",
            5,
            "Sunny intervals and light winds"
        )

        val city = "beijing"
        val data = mock<WeatherData> {
            onBlocking { getWeatherInfoFor(city) } doReturn (weatherInfo)
        }
        val useCase = WeatherUseCaseImpl(data)
        val result = runBlocking { useCase.getWeatherInfoFor(city) }
        assertEquals(weatherInfo, result)
    }
}