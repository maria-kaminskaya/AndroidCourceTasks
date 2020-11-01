package com.kmnvxh222.task8.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kmnvxh222.task8.model.City

@Dao
interface CityDao {
    @Insert
    fun insertNewCity(city: City)

    @Update
    fun updateCity(city: City)

    @Query("SELECT * FROM citys")
    fun getAllLiveData(): LiveData<List<City>>?

}