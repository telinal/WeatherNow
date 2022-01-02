package com.example.weathernow

import android.app.Application
import com.example.weathernow.api.RetrofitHelper
import com.example.weathernow.api.WeatherAPI
import com.example.weathernow.repository.WeatherRepository

class WeatherApplication: Application() {
    lateinit var currentWeatherRepository: WeatherRepository

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        val weatherAPI = RetrofitHelper.getInstance().create(WeatherAPI::class.java)
        currentWeatherRepository = WeatherRepository(weatherAPI, applicationContext)
    }
}