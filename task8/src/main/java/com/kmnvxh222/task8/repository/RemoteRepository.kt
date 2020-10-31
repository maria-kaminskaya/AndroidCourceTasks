package com.kmnvxh222.task8.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kmnvxh222.task8.model.WeatherResponse
import com.kmnvxh222.task8.network.RetrofitApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RemoteRepository: RemoteRepositoryInterface{
    private var job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

  override fun getDataWeather(city: String): MutableLiveData<WeatherResponse?> {
        val weatherData = MutableLiveData<WeatherResponse?>()
        try {
            coroutineScope.launch {
                val response = RetrofitApi.retrofitApiService.getWeatherCity(city)
                weatherData.value = response.await()
                Log.d("RemoteRepository", " getDataWeather ${response.isCompleted}")
                Log.d("RemoteRepository", " getDataWeather ${weatherData.value}")
            }
        } catch (e: Error) {
            Log.d("RemoteRepository", "error getDataWeather ${e}")
        }
        return weatherData
    }

    fun close() {
        job.cancel()
    }
}