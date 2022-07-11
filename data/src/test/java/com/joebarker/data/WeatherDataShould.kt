package com.joebarker.data

import com.joebarker.domain.entities.WeatherInfo
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import retrofit2.Response
import retrofit2.mock.Calls
import java.lang.Exception

class WeatherDataShould {
    private val expectedCity = "City"
    private val expectedCountry = "Country"
    private val expectedDescription = "Description description"
    private val expectedTemperature = 5
    private val errorJson = "{\n" +
            "    \"success\": false,\n" +
            "    \"status_code\": 404,\n" +
            "    \"status_message\": \"Not Found\"\n" +
            "}"

    @Test
    fun retrieveWeatherInfoFromApi() {
        val response = WeatherInfoResponse(expectedCity, expectedCountry, expectedTemperature, expectedDescription)
        val passedInCity = "city"
        val remoteCalls = mock<WeatherRemoteCalls> {
            on { retrieveWeatherInfo(passedInCity) } doReturn(Calls.response(response))
        }
        val weatherData = WeatherDataImpl(remoteCalls)
        val expected = WeatherInfo(expectedCity, expectedCountry, expectedTemperature, expectedDescription)
        val result = runBlocking { weatherData.getWeatherInfoFor(passedInCity) }
        assertEquals(expected, result)
    }

    @Test
    fun throwExceptionWhenFailure() {
        val response = Response.error<WeatherInfoResponse>(404, ResponseBody.create(MediaType.parse("application/json"), errorJson))
        val passedInCity = "city"
        val remoteCalls = mock<WeatherRemoteCalls> {
            on { retrieveWeatherInfo(passedInCity) } doReturn(Calls.response(response))
        }
        val weatherData = WeatherDataImpl(remoteCalls)
        assertThrows(Exception::class.java) {
            runBlocking { weatherData.getWeatherInfoFor(passedInCity) }
        }
    }

}