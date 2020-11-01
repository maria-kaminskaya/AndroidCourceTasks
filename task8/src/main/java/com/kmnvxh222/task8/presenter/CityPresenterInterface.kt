package com.kmnvxh222.task8.presenter

import androidx.lifecycle.LiveData
import com.kmnvxh222.task8.model.City

interface CityPresenterInterface {
    fun addCity(city: City)
    fun getAllCity(): LiveData<List<City>>?
    fun updateCity(city: City)
    fun close()
}