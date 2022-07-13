package com.joebarker.domain.boundary.data

import com.joebarker.domain.entities.Either
import com.joebarker.domain.entities.ErrorEntity
import com.joebarker.domain.entities.WeatherInfo

interface WeatherData {
    suspend fun getWeatherInfoFor(city: String): Either<WeatherInfo?, ErrorEntity>
}
