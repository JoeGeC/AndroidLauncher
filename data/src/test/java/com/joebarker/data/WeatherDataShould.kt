package com.joebarker.data

import com.joebarker.domain.entities.Either
import com.joebarker.domain.entities.ErrorEntity
import com.joebarker.domain.entities.ErrorMessage
import com.joebarker.domain.entities.WeatherInfo
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

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
    private val responseJson = "{\n\t\"city\": \"$expectedCity\",\n\t\"country\": \"$expectedCountry\",\n\t\"temperature\": $expectedTemperature, \n\t\"description\": \"$expectedDescription\"\n}\n"

    @Test
    fun retrieveWeatherInfoFromApi() {
        val contentRetrieverMock = ContentRetrieverMock(responseJson)
        val weatherData = WeatherDataImpl(contentRetrieverMock)
        val expected = Either.Success(WeatherInfo(expectedCity, expectedCountry, expectedTemperature, expectedDescription))
        val result = runBlocking { weatherData.getWeatherInfoFor(cityToGet) }
        assertEquals(expected, result)
    }

    @Test
    fun returnErrorWhenErrorResponse() {
        val contentRetrieverMock = ContentRetrieverMock(errorJson)
        val weatherData = WeatherDataImpl(contentRetrieverMock)
        val expected = Either.Failure(ErrorEntity(ErrorMessage(errorMessage)))
        val result = runBlocking { weatherData.getWeatherInfoFor(cityToGet) }
        assertEquals(expected, result)
    }

}