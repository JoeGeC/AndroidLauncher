package com.joebarker.config

import com.joebarker.data.WeatherDataImpl
import com.joebarker.domain.usecases.WeatherUseCaseImpl

class Config {

    private val weatherData by lazy { WeatherDataImpl() }
    val weatherUseCase by lazy { WeatherUseCaseImpl(weatherData) }

}