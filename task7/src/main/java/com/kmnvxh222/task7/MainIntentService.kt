package com.kmnvxh222.task7

import android.app.IntentService
import android.content.Intent
import com.kmnvxh222.task7.dataStorage.DataStorageInterface
import com.kmnvxh222.task7.dataStorage.ExternalDataStorage
import com.kmnvxh222.task7.dataStorage.InternalDataStorage
import com.kmnvxh222.task7.model.Data
import com.kmnvxh222.task7.settings.SharedPreferencesSettings
import java.util.*


class MainIntentService(name: String? = "MainIntentService") : IntentService(name) {

    private var data: Data? = null
    private lateinit var dataStorage: DataStorageInterface
    private val settingsSharedPreferences = SharedPreferencesSettings()

    override fun onHandleIntent(intent: Intent?) {
        val date = Calendar.getInstance()
        val action = intent?.getStringExtra("action")

        data = Data(date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1, date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.HOUR), date.get(Calendar.MINUTE), action)

        writeDataInFile(data!!)
    }

    private fun writeDataInFile(data: Data) {
        definitionTypeDataStorage(settingsSharedPreferences.getSetting(applicationContext))
        dataStorage.writeData(applicationContext, data)
    }

    private fun definitionTypeDataStorage(typeDataStorage: String?) {
        when (typeDataStorage) {
            resources.getString(R.string.externalDataStorage) -> dataStorage = ExternalDataStorage()
            resources.getString(R.string.internalDataStorage) -> dataStorage = InternalDataStorage()
        }
    }
}
