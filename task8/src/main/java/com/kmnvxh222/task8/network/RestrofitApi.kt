package com.kmnvxh222.task8.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApi {
    private const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val retrofitApiService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}

