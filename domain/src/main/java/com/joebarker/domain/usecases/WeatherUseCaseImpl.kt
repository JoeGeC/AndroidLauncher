package com.joebarker.domain.usecases

import com.joebarker.domain.boundary.data.WeatherData
import com.joebarker.domain.boundary.output.WeatherUseCase
import com.joebarker.domain.entities.Either
import com.joebarker.domain.entities.ErrorEntity
import com.joebarker.domain.entities.WeatherInfo

class WeatherUseCaseImpl(private val data: WeatherData): WeatherUseCase {

    override suspend fun getWeatherInfoFor(city: String): Either<WeatherInfo?, ErrorEntity> =
        data.getWeatherInfoFor(city)

}
