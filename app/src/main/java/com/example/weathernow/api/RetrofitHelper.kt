package com.example.weathernow.api

import android.util.Log
import com.example.weathernow.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.Buffer
import java.util.concurrent.TimeUnit

object RetrofitHelper {

    val BASE_URL= "http://api.weatherstack.com/"
    val OKHTTP= "okhttp"
    var okHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val request: Request = it.request()
            if (BuildConfig.DEBUG) {
                Log.d(OKHTTP, request.method().toString() + " " + request.url())
                Log.d(OKHTTP, "" + request.header("Cookie"))
                val buffer = okio.Buffer()
                request.body()?.writeTo(buffer)
                Log.d(OKHTTP, "Payload- " + buffer.readUtf8())
            }
            it.proceed(request)
        }
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}