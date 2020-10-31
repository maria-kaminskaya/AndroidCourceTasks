package com.kmnvxh222.task8.presenter

import androidx.lifecycle.LiveData
import com.kmnvxh222.task8.model.City

interface CityPresenterInterface {
    fun addCity(city: City)
    fun deleteCity(city: City)
    fun getAllCity(): LiveData<List<City>>?
    fun getCityById(id: Long): LiveData<City>?
    fun deleteAllCitys()
}