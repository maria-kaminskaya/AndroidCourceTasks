package com.kmnvxh222.task8.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.kmnvxh222.task8.db.CityDao
import com.kmnvxh222.task8.db.getAppDatabase
import com.kmnvxh222.task8.model.City
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LocalRepository(context: Context) : CityDao {

    private val dbDao = getAppDatabase(context).cityDao()!!

    private var job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)


    override fun insertNewCity(city: City) {
        try {
            coroutineScope.launch {
                dbDao.insertNewCity(city)
            }
        } catch (e: Exception) {
            Log.d("LocalRepository", "error insertNewCity ${e}")
        }
    }

    override fun updateCity(city: City) {
        try {
            coroutineScope.launch {
                dbDao.updateCity(city)
            }
        } catch (e: Exception) {
            Log.d("LocalRepository", "error updateCity ${e}")
        }
    }

    override fun getAllLiveData(): LiveData<List<City>>? {
        var listCitys: LiveData<List<City>>? = null
//        try {
//            coroutineScope.launch {
        listCitys = dbDao.getAllLiveData()
//            }
//        } catch (e: Exception) {
//            Log.d("LocalRepository", "error getAllLiveData ${e}")
//        }
        return listCitys
    }

    fun close(){
        job.cancel()
    }

}