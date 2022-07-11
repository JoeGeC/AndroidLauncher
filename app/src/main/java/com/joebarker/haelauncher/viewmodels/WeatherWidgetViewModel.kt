package com.joebarker.haelauncher.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joebarker.domain.boundary.output.WeatherUseCase
import com.joebarker.domain.entities.WeatherInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherWidgetViewModel(
    private val useCase: WeatherUseCase
): ViewModel() {
    val weatherInfo: LiveData<WeatherInfo> get() = _weatherInfo
    private val _weatherInfo = MutableLiveData<WeatherInfo>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _isLoading = MutableLiveData(false)

    fun retrieveWeatherInfoFor(city: String, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            _isLoading.postValue(true)
            withContext(dispatcher){
                _weatherInfo.postValue(useCase.getWeatherInfoFor(city))
            }
            _isLoading.postValue(false)
        }
    }

}
