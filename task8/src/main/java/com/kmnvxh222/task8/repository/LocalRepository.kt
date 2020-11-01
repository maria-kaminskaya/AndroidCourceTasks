package com.kmnvxh222.task8.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kmnvxh222.task8.db.CityDao
import com.kmnvxh222.task8.db.getAppDatabase
import com.kmnvxh222.task8.model.City
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LocalRepository(context: Context) : CityDao {

    private val dbDao = getAppDatabase(context).cityDao()!!

    private var job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)


    override fun insertNewCity(city: City) {
        try {
            coroutineScope.launch {
                dbDao.insertNewCity(city)
                Log.d("LocalRepository", " insertNewCity")
            }
        } catch (e: Exception) {
            Log.d("LocalRepository", "error insertNewCity ${e}")
        }
    }

    override fun updateCity(city: City) {
        try {
            coroutineScope.launch {
                dbDao.updateCity(city)
                Log.d("LocalRepository", " updateCity")
            }
        } catch (e: Exception) {
            Log.d("LocalRepository", "error updateCity ${e}")
        }
    }

    override fun deleteCity(city: City) {
        try {
            coroutineScope.launch {
                dbDao.deleteCity(city)
                Log.d("LocalRepository", " deleteCity")
            }
        } catch (e: Exception) {
            Log.d("LocalRepository", "error deleteCity ${e}")
        }
    }

    override fun getAllLiveData(): LiveData<List<City>>? {
        var listCitys: LiveData<List<City>>? = null
//        try {
//            coroutineScope.launch {
                listCitys = dbDao.getAllLiveData()
                Log.d("LocalRepository", " getAllLiveData ${listCitys?.value}")
//            }
//        } catch (e: Exception) {
//            Log.d("LocalRepository", "error getAllLiveData ${e}")
//        }
        return listCitys
    }

    override fun getCityById(id: Long): LiveData<City>? {
        var city: LiveData<City>? = null
        try {
            coroutineScope.launch {
                city = dbDao.getCityById(id)
                Log.d("LocalRepository", " getCityById")
                Log.d("LocalRepository", " getCityById ${city?.value}")
            }
        } catch (e: Exception) {
            Log.d("LocalRepository", "error getCityById ${e}")
        }
        return city
    }

    override fun deleteAllCitys() {
        try {
            coroutineScope.launch {
                dbDao.deleteAllCitys()
                Log.d("LocalRepository", " deleteAllCitys")
            }
        } catch (e: Exception) {
            Log.d("LocalRepository", "error deleteAllCitys ${e}")
        }
    }


}