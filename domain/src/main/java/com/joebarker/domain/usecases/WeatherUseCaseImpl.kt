package com.joebarker.domain.usecases

import com.joebarker.domain.boundary.data.WeatherData
import com.joebarker.domain.entities.WeatherInfo

class WeatherUseCaseImpl(private val data: WeatherData) {

    fun getWeatherInfoFor(city: String): WeatherInfo =
        data.getWeatherInfoFor(city)

}
