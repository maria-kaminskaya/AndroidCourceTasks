package com.kmnvxh222.task7.dataStorage

import android.content.Context
import com.kmnvxh222.task7.model.Data

interface DataStorageInterface {

    companion object{
        const val FILE_NAME = "broadcast_events"
        fun dataMapper(data: Data) = "${data.year}/${data.month}/${data.day}_${data.hours}:${data.minutes}-${data.name} "

    }

    fun writeData(context: Context, data: Data){}
    fun readData(context: Context): String {
        return ""
    }
}