package com.joebarker.data

import com.joebarker.domain.entities.WeatherInfo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import retrofit2.mock.Calls

class WeatherDataShould {
    private val expectedCity = "City"
    private val expectedCountry = "Country"
    private val expectedDescription = "Description description"
    private val expectedTemperature = 5

    @Test
    fun retrieveWeatherInfoFromApi() {
        val response = WeatherInfoResponse(expectedCity, expectedCountry, expectedTemperature, expectedDescription)
        val passedInCity = "city"
        val remoteCalls = mock<WeatherRemoteCalls> {
            on { retrieveWeatherInfo(passedInCity) } doReturn(Calls.response(response))
        }
        val weatherData = WeatherDataImpl(remoteCalls)
        val expected = WeatherInfo(expectedCity, expectedCountry, expectedTemperature, expectedDescription)
        assertEquals(expected, weatherData.getWeatherInfoFor(passedInCity))
    }

}