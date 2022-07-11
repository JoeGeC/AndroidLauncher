package com.joebarker.haelauncher

import com.joebarker.domain.boundary.output.WeatherUseCase
import com.joebarker.domain.entities.WeatherInfo
import com.joebarker.haelauncher.viewmodels.WeatherWidgetViewHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class WeatherWidgetViewHolderShould {

    @Test
    fun retrieveWeatherInfoFromUseCase() {
        val weatherInfo = WeatherInfo(
            "Beijing",
            "China",
            5,
            "Sunny intervals and light winds"
        )
        val useCase = mock<WeatherUseCase> {
            onBlocking { getWeatherInfoFor(WeatherWidgetViewHolder.BEIJING) }.doReturn(weatherInfo)
        }
        val viewModel = WeatherWidgetViewHolder(useCase)
        runBlocking {
            viewModel.retrieveWeatherInfoFor(WeatherWidgetViewHolder.BEIJING, Dispatchers.Unconfined)
        }
        assertEquals(weatherInfo, viewModel.weatherInfo.value)
    }

}