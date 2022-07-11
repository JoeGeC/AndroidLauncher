package com.joebarker.domain.boundary.data

import com.joebarker.domain.entities.WeatherInfo

interface WeatherData {
    fun getWeatherInfoFor(city: String): WeatherInfo?
}
