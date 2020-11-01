package com.kmnvxh222.task8.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kmnvxh222.task8.model.WeatherResponse
import com.kmnvxh222.task8.repository.LocalRepository
import com.kmnvxh222.task8.repository.RemoteRepository
import com.kmnvxh222.task8.repository.RemoteRepositoryInterface

class WeatherViewModel(context: Context) : ViewModel() {

    private val remoteRepository: RemoteRepositoryInterface = RemoteRepository()
    private val localRepository: LocalRepository = LocalRepository(context)

    fun getWeatherDate(city: String): MutableLiveData<WeatherResponse?> {

        Log.d("WeatherViewModel", " getDataWeather ${remoteRepository.getDataWeather(city)}")
        return remoteRepository.getDataWeather(city) }

    fun getAllCity() = localRepository.getAllLiveData()

    fun close(){
        localRepository.close()
        remoteRepository.close()
    }
}