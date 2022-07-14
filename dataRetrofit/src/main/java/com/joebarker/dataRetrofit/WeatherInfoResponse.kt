package com.joebarker.dataRetrofit

import com.joebarker.domain.entities.WeatherInfo

data class WeatherInfoResponse(
    val city: String?,
    val country: String?,
    val temperature: Int?,
    val description: String?
) {
    fun convert(): WeatherInfo = WeatherInfo(
        city, country, temperature, description
    )

}