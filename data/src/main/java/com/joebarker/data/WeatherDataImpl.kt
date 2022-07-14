package com.joebarker.data

import com.joebarker.domain.boundary.data.WeatherData
import com.joebarker.domain.entities.Either
import com.joebarker.domain.entities.ErrorEntity
import com.joebarker.domain.entities.ErrorMessage
import com.joebarker.domain.entities.WeatherInfo
import org.json.JSONException
import org.json.JSONObject

class WeatherDataImpl(
    private val contentRetriever: ContentRetriever = HttpJsonRetriever()
) : WeatherData {
    private val baseUrl = "https://weather.bfsah.com/"
    private val errorMessage = "Not found"
    private val cityJsonLabel = "city"
    private val countryJsonLabel = "country"
    private val temperatureJsonLabel = "temperature"
    private val descriptionJsonLabel = "description"

    override suspend fun getWeatherInfoFor(city: String): Either<WeatherInfo?, ErrorEntity> {
        val data = contentRetriever.getContentFromUrl("$baseUrl$city")
        if (data.isEmpty()) return Either.Failure(ErrorEntity(ErrorMessage(errorMessage)))
        return try {
            val jsonObject = JSONObject(data)
            Either.Success(
                WeatherInfo(
                    jsonObject.getString(cityJsonLabel),
                    jsonObject.getString(countryJsonLabel),
                    jsonObject.getInt(temperatureJsonLabel),
                    jsonObject.getString(descriptionJsonLabel)))
        } catch (e: JSONException) {
            Either.Failure(ErrorEntity(ErrorMessage(errorMessage)))
        }
    }
}