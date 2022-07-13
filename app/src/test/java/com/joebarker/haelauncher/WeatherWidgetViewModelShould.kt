package com.joebarker.haelauncher

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.joebarker.domain.boundary.output.WeatherUseCase
import com.joebarker.domain.entities.Either
import com.joebarker.domain.entities.ErrorEntity
import com.joebarker.domain.entities.ErrorMessage
import com.joebarker.domain.entities.WeatherInfo
import com.joebarker.haelauncher.viewmodels.WeatherWidgetViewModel
import com.joebarker.haelauncher.viewmodels.observeForTesting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import java.lang.NullPointerException

class WeatherWidgetViewModelShould {
    private val cityToGet = "beijing"

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun retrieveWeatherInfoFromUseCase() {
        val weatherInfo = WeatherInfo(
            "Beijing",
            "China",
            5,
            "Sunny intervals and light winds"
        )
        val useCase = mock<WeatherUseCase> {
            onBlocking { getWeatherInfoFor(cityToGet) } doReturn(Either.Success(weatherInfo))
        }
        val viewModel = WeatherWidgetViewModel(useCase)

        runBlocking { viewModel.retrieveWeatherInfoFor(cityToGet, Dispatchers.Unconfined) }

        viewModel.weatherInfo.observeForTesting {
            assertEquals(weatherInfo, viewModel.weatherInfo.value)
        }

    }

    @Test
    fun showErrorWhenFailureResponse(){
        val errorMessage = "error"
        val result = Either.Failure(ErrorEntity(ErrorMessage(errorMessage)))
        val useCase = mock<WeatherUseCase>{
            onBlocking { getWeatherInfoFor(cityToGet) }.doReturn(result)
        }
        val viewModel = WeatherWidgetViewModel(useCase)

        runBlocking { viewModel.retrieveWeatherInfoFor(cityToGet, Dispatchers.Unconfined) }

        assertEquals(errorMessage, viewModel.errorMessage)
    }

    @Test
    fun showErrorWhenException(){
        val useCase = mock<WeatherUseCase>{
            onBlocking { getWeatherInfoFor(cityToGet) } doThrow(NullPointerException())
        }
        val viewModel = WeatherWidgetViewModel(useCase)

        runBlocking { viewModel.retrieveWeatherInfoFor(cityToGet, Dispatchers.Unconfined) }

        assertEquals("Error", viewModel.errorMessage)
    }

}