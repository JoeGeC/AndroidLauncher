package com.joebarker.domain

import com.joebarker.domain.boundary.data.WeatherData
import com.joebarker.domain.entities.Either
import com.joebarker.domain.entities.ErrorEntity
import com.joebarker.domain.entities.WeatherInfo
import com.joebarker.domain.usecases.WeatherUseCaseImpl
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class WeatherUseCaseShould {
    private val weatherInfo = WeatherInfo(
        "Beijing",
        "China",
        5,
        "Sunny intervals and light winds"
    )
    private val cityToGet = "beijing"

    @Test
    fun returnWeatherInfoFromData() {
        val expected = Either.Success(weatherInfo)
        assertResultFromData(expected)
    }

    @Test
    fun returnFailureFromData() {
        val expected = Either.Failure(ErrorEntity("City not found"))
        assertResultFromData(expected)
    }

    private fun assertResultFromData(expected: Either<WeatherInfo, ErrorEntity>) {
        val data = mock<WeatherData> {
            onBlocking { getWeatherInfoFor(cityToGet) } doReturn (expected)
        }
        val useCase = WeatherUseCaseImpl(data)
        val result = runBlocking { useCase.getWeatherInfoFor(cityToGet) }
        assertEquals(expected, result)
    }
}