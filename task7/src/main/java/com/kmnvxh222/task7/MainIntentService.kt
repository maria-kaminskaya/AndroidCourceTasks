package com.kmnvxh222.task7

import android.app.IntentService
import android.content.Intent
import com.kmnvxh222.task7.dataStorage.ExternalDataStorage
import com.kmnvxh222.task7.dataStorage.InternalDataStorage
import com.kmnvxh222.task7.model.Data
import com.kmnvxh222.task7.settings.SharedPreferencesSettings
import java.util.*


class MainIntentService(name: String? = "MainIntentService") : IntentService(name) {

    private var data: Data? = null
    private val externalDataStorage = ExternalDataStorage()
    private val internalDataStorage = InternalDataStorage()
    private val settingsSharedPreferences = SharedPreferencesSettings()

    override fun onHandleIntent(intent: Intent?) {
        val date = Calendar.getInstance()
        val action = intent?.getStringExtra("action")

        data = Data(date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1, date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.HOUR), date.get(Calendar.MINUTE), action)

        writeDataInFile(data!!)
    }

    private fun writeDataInFile(data: Data) {
        when (settingsSharedPreferences.getSetting(applicationContext)) {
            resources.getString(R.string.externalDataStorage) -> externalDataStorage.writeData(applicationContext, data)
            resources.getString(R.string.internalDataStorage) -> internalDataStorage.writeData(applicationContext, data)
        }
    }
}
