package com.kmnvxh222.task7.dataStorage

import android.content.Context
import android.content.Context.MODE_APPEND
import android.util.Log
import com.kmnvxh222.task7.dataStorage.DataStorageInterface.Companion.FILE_NAME
import com.kmnvxh222.task7.dataStorage.DataStorageInterface.Companion.dataMapper
import com.kmnvxh222.task7.model.Data


class InternalDataStorage : DataStorageInterface {

    override fun readData(context: Context): String {
        var text = ""
        try {
            context.openFileInput(FILE_NAME).use { stream ->
                text = stream.bufferedReader().use {
                    it.readText()
                }
            }
        } catch (e: Exception) {
            Log.d("InternalDataStorage", "readData ${e}")
            return e.toString()
        }
        return text
    }

    override fun writeData(context: Context, data: Data) {
        try {
            context.openFileOutput(FILE_NAME, MODE_APPEND).use { output ->
                output.write(dataMapper(data).toByteArray())
            }
        } catch (e: Exception) {
            Log.d("InternalDataStorage", "writeData ${e}")
        }
    }
}
