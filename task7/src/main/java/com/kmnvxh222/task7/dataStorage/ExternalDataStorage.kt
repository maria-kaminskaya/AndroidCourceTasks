package com.kmnvxh222.task7.dataStorage

import android.content.Context
import android.os.Environment
import android.util.Log
import com.kmnvxh222.task7.dataStorage.DataStorageInterface.Companion.FILE_NAME
import com.kmnvxh222.task7.dataStorage.DataStorageInterface.Companion.dataMapper
import com.kmnvxh222.task7.model.Data
import java.io.File


class ExternalDataStorage : DataStorageInterface {
    
    override fun readData(context: Context): String {
        var text = ""
        if (isExternalStorageReadable()) {
            try {
                val sdPath = context.getExternalFilesDir(null)
                val sdFile = File(sdPath, FILE_NAME)
                val inp = sdFile.inputStream()
                val bytes = ByteArray(inp.available())
                inp.read(bytes)
                text = String(bytes)
            } catch (e: Exception) {
                Log.d("ExternalDataStorage", "readData ${e}")
                return e.toString()
            }
        }
        return text
    }

    override fun writeData(context: Context, data: Data) {
        try {
            if (isExternalStorageWritable()) {
                val file = File(context.getExternalFilesDir(null), FILE_NAME)
                file.appendText(dataMapper(data))
            }
        } catch (e: Exception) {
            Log.d("ExternalDataStorage", "writeData ${e}")
        }

    }

    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    private fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

}