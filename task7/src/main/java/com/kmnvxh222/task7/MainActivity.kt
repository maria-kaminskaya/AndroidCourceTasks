package com.kmnvxh222.task7

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kmnvxh222.task7.dataStorage.DataStorageInterface
import com.kmnvxh222.task7.dataStorage.ExternalDataStorage
import com.kmnvxh222.task7.dataStorage.InternalDataStorage
import com.kmnvxh222.task7.settings.SettingsDialogFragment
import com.kmnvxh222.task7.settings.SharedPreferencesSettings
import kotlinx.android.synthetic.main.activity_main.buttonSetting
import kotlinx.android.synthetic.main.activity_main.textViewFile
import kotlinx.android.synthetic.main.activity_main.textViewTypeStorage

class MainActivity : AppCompatActivity() {

    private val broadcastReceiver = MainBroadcastReceiver()
    private val settingsSharedPreferences = SharedPreferencesSettings()
    private lateinit var dataStorage: DataStorageInterface
    private var typeDataStorage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registrationBroadcastReceiver()
        settingsClick()
        setData()
    }

    private fun registrationBroadcastReceiver() {
        val filter = IntentFilter(Intent.ACTION_SCREEN_ON).apply {
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(Intent.ACTION_POWER_CONNECTED)
        }
        registerReceiver(broadcastReceiver, filter)
    }

    private fun settingsClick() {
        buttonSetting.setOnClickListener {
            val manager = supportFragmentManager
            SettingsDialogFragment().show(manager, "SettingsDialog")
        }
    }

    private fun setData() {
        typeDataStorage = settingsSharedPreferences.getSetting(applicationContext)
        textViewTypeStorage.text = typeDataStorage
        definitionTypeDataStorage(typeDataStorage)
        textViewFile.text = dataStorage.readData(applicationContext)
    }

    private fun definitionTypeDataStorage(typeDataStorage: String?) {
        when (typeDataStorage) {
            resources.getString(R.string.externalDataStorage) -> dataStorage = ExternalDataStorage()
            resources.getString(R.string.internalDataStorage) -> dataStorage = InternalDataStorage()
        }
    }

    override fun onResume() {
        super.onResume()
        setData()
    }
}
