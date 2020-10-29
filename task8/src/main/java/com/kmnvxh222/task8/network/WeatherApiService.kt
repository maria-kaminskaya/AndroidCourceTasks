package com.kmnvxh222.task8.network

import com.kmnvxh222.task8.model.WeatherResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService{

    companion object {
        private const val API_KEY = "42725699ffa2e29ae4f1a8945db4bab6"
        private const val EXCLUDE = "minutely,alerts,daily"
    }

    @GET("forecast?appid=$API_KEY")
    fun getWeatherCity(
            @Query("city") city: String
    ): Deferred<WeatherResponse>
}
