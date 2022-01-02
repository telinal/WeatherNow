package com.example.weathernow.models

import com.example.weathernow.R

data class Current(
    val cloudcover: Int,
    val precip: Int,
    val pressure: Int,
    val temperature: Int,
    val weather_icons: List<String>,
    val weather_descriptions: List<String>,
    val wind_speed: Int
)
// {
//    fun getIcon(): Int{
//        return when(weather_descriptions[0]){
//            "Haze" -> R.drawable.ic_launcher_background
//            else -> R.drawable.ic_launcher_foreground
//        }
//    }
//}