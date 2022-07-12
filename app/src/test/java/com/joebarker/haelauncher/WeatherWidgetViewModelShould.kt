package com.joebarker.haelauncher

import com.joebarker.domain.boundary.output.WeatherUseCase
import com.joebarker.domain.entities.WeatherInfo
import com.joebarker.haelauncher.viewmodels.WeatherWidgetViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class WeatherWidgetViewModelShould {

    @Test
    fun retrieveWeatherInfoFromUseCase() {
        val city = "beijing"
        val weatherInfo = WeatherInfo(
            "Beijing",
            "China",
            5,
            "Sunny intervals and light winds"
        )
        val useCase = mock<WeatherUseCase> {
            onBlocking { getWeatherInfoFor(city) } doReturn(weatherInfo)
        }
        val viewModel = WeatherWidgetViewModel(useCase)
        runBlocking {
            viewModel.retrieveWeatherInfoFor(city, Dispatchers.Unconfined)
        }
        assertEquals(weatherInfo, viewModel.weatherInfo.value)
    }

}