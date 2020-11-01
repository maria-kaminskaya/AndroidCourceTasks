package com.kmnvxh222.task8.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kmnvxh222.task8.model.City

@Database(entities = [City::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao?
}

fun getAppDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "database"
    )
            .allowMainThreadQueries()
            .build()
}