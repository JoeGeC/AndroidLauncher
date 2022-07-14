package com.joebarker.dataRetrofit

import com.joebarker.domain.entities.Either
import com.joebarker.domain.entities.ErrorEntity
import com.joebarker.domain.entities.ErrorMessage
import com.joebarker.domain.entities.WeatherInfo
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import retrofit2.Response
import retrofit2.mock.Calls

class WeatherDataShould {
    private val cityToGet = "city"
    private val expectedCity = "City"
    private val expectedCountry = "Country"
    private val expectedDescription = "Description description"
    private val expectedTemperature = 5
    private val errorMessage = "Not found"
    private val errorJson = "{\n" +
            "    \"error\": {\n" +
            "        \"code\": 404,\n" +
            "        \"message\": \"$errorMessage\"\n" +
            "    }" +
            "}"

    @Test
    fun retrieveWeatherInfoFromApi() {
        val response = WeatherInfoResponse(expectedCity, expectedCountry, expectedTemperature, expectedDescription)
        val remoteCalls = mock<WeatherRemoteCalls> {
            on { retrieveWeatherInfo(cityToGet) } doReturn(Calls.response(response))
        }
        val weatherData = WeatherDataImpl(remoteCalls)
        val expected = Either.Success(WeatherInfo(expectedCity, expectedCountry, expectedTemperature, expectedDescription))
        val result = runBlocking { weatherData.getWeatherInfoFor(cityToGet) }
        assertEquals(expected, result)
    }

    @Test
    fun returnErrorWhenErrorResponse() {
        val response = Response.error<WeatherInfoResponse>(404, ResponseBody.create(MediaType.parse("application/json"), errorJson))
        val remoteCalls = mock<WeatherRemoteCalls> {
            on { retrieveWeatherInfo(cityToGet) } doReturn(Calls.response(response))
        }
        val weatherData = WeatherDataImpl(remoteCalls)
        val expected = Either.Failure(ErrorEntity(ErrorMessage(errorMessage)))
        val result = runBlocking { weatherData.getWeatherInfoFor(cityToGet) }
        assertEquals(expected, result)
    }

}