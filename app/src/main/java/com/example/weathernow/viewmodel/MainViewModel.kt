package com.example.weathernow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathernow.models.Current
import com.example.weathernow.models.WeatherResponse
import com.example.weathernow.repository.Response
import com.example.weathernow.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(val repository: WeatherRepository): ViewModel() {

    val currentLive: LiveData<Response<WeatherResponse>>
    get() = repository.weatherData

    fun getCurrentWeather(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCurrentWeather(query)
        }
    }
}