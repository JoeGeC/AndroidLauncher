package com.joebarker.domain.entities

data class WeatherInfo(
    val city: String?,
    val country: String?,
    val temperature: Int?,
    val description: String?
)