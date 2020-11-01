package com.kmnvxh222.task8.presenter

import androidx.lifecycle.LiveData
import com.kmnvxh222.task8.model.City
import com.kmnvxh222.task8.repository.LocalRepository
import com.kmnvxh222.task8.view.CityFragment

class CityPresenter(private var localRepository: LocalRepository): CityPresenterInterface{

    override fun addCity(city: City) {
        localRepository.insertNewCity(city)
    }

    override fun getAllCity(): LiveData<List<City>>? = localRepository.getAllLiveData()

    override fun updateCity(city: City) {
        localRepository.updateCity(city)
    }

    override fun close() {
        localRepository.close()
    }

}