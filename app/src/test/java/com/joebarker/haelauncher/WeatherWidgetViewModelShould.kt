package com.joebarker.haelauncher

import com.joebarker.domain.boundary.output.WeatherUseCase
import com.joebarker.domain.entities.Either
import com.joebarker.domain.entities.ErrorEntity
import com.joebarker.domain.entities.ErrorMessage
import com.joebarker.haelauncher.viewmodels.WeatherWidgetViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock

class WeatherWidgetViewModelShould {
    private val cityToGet = "beijing"

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