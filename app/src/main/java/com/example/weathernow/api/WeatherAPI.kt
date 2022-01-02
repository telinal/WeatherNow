package com.example.weathernow.api

import com.example.weathernow.BuildConfig
import com.example.weathernow.models.Current
import com.example.weathernow.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherAPI {

    @GET("/current")
    suspend fun getCurrentWeather(
        @Header("access_key") apiKey:String = BuildConfig.API_KEY,
        @Query("query") query: String
    ) : Response<WeatherResponse>
}