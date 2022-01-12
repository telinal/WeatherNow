package com.example.weathernow.models

import com.example.weathernow.R
import java.util.*

data class Current(
    val cloudcover: Int,
    val precip: Int,
    val pressure: Int,
    val temperature: Int,
    val weather_icons: List<String>,
    val weather_descriptions: List<String>,
    val wind_speed: Int
)
 {
    fun getIcon(): String{
        return when(weather_descriptions[0]){
            "Haze" -> "https://cdn-icons.flaticon.com/png/512/5370/premium/5370331.png?token=exp=1641555761~hmac=c2be3100a22901e07fcac477e0d37e3b"
            "Mist" -> "https://cdn-icons-png.flaticon.com/512/4005/4005817.png"
            "Haze, Mist" -> "https://cdn-icons-png.flaticon.com/512/990/990469.png"
            else -> weather_icons[0]
        }
    }
}