package com.joebarker.haelauncher.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joebarker.domain.boundary.output.WeatherUseCase
import com.joebarker.domain.entities.WeatherInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class WeatherWidgetViewModel(
    private val useCase: WeatherUseCase
): ViewModel() {
    val weatherInfo: LiveData<WeatherInfo> get() = _weatherInfo
    private val _weatherInfo = MutableLiveData<WeatherInfo>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _isLoading = MutableLiveData(false)
    val isError: LiveData<Boolean> get() = _isError
    private val _isError = MutableLiveData(false)
    var errorMessage = ""

    fun retrieveWeatherInfoFor(city: String, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            withContext(dispatcher){
                _isError.postValue(false)
                _isLoading.postValue(true)
                try{
                    val weatherInfo = useCase.getWeatherInfoFor(city)
                    if(weatherInfo.isSuccess)
                        _weatherInfo.postValue(weatherInfo.body)
                    else showError(weatherInfo.errorBody.error.message)
                } catch (e: Exception){
                    showError("Error")
                }
                _isLoading.postValue(false)
            }
        }
    }

    private fun showError(newErrorMessage: String) {
        errorMessage = newErrorMessage
        _isError.postValue(true)
    }

}

fun <T> LiveData<T>.observeForTesting(block: () -> Unit) {
    val observer = Observer<T> { }
    try {
        observeForever(observer)
        block()
    } finally {
        removeObserver(observer)
    }
}