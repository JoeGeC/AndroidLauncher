package com.joebarker.domain.boundary.data

import com.joebarker.domain.entities.WeatherInfo

interface WeatherData {
    suspend fun getWeatherInfoFor(city: String): WeatherInfo?
}
