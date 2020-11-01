package com.kmnvxh222.task8.repository

import androidx.lifecycle.MutableLiveData
import com.kmnvxh222.task8.model.WeatherResponse

interface RemoteRepositoryInterface
{
    fun getDataWeather(city: String): MutableLiveData<WeatherResponse?>
    fun close()
}