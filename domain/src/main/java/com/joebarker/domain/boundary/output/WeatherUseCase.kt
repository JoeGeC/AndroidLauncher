package com.joebarker.domain.boundary.output

import com.joebarker.domain.entities.Either
import com.joebarker.domain.entities.ErrorEntity
import com.joebarker.domain.entities.WeatherInfo

interface WeatherUseCase {
    suspend fun getWeatherInfoFor(city: String): Either<WeatherInfo?, ErrorEntity>
}