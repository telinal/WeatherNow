package com.example.weathernow.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weathernow.api.WeatherAPI
import com.example.weathernow.models.Current
import com.example.weathernow.models.WeatherResponse
import com.example.weathernow.utils.NetworkUtils

class WeatherRepository(
    private val weatherAPI: WeatherAPI,
    private val applicationContext: Context
    ) {
    private val weatherLiveData = MutableLiveData<Response<WeatherResponse>>()
    val weatherData: LiveData<Response<WeatherResponse>>
    get() = weatherLiveData

    suspend fun getCurrentWeather(query: String) {
        weatherLiveData.postValue(Response.Loading())
        if (NetworkUtils.isInternetAvailable(applicationContext)) {
            try {
                val result = weatherAPI.getCurrentWeather(query = query)
                if (result?.body() != null) {
                    weatherLiveData.postValue(Response.Success(result.body()))
                }
            } catch (e: Exception) {
                weatherLiveData.postValue(Response.Error(e.message.toString()))
            }
        }
        else{
            weatherLiveData.postValue(Response.Error("No Internet Connection"))
        }
    }
}