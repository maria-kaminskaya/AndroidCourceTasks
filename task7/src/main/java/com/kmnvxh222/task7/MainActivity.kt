package com.kmnvxh222.task7

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    private val externalDataStorage = ExternalDataStorage()
    private val internalDataStorage = InternalDataStorage()
    private val settingsDialogFragment = SettingsDialogFragment()
    private var typeDataStorage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registrationBroadcastReceiver()
        settingsClick()
        setTypeDataStorage()
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
            settingsDialogFragment.show(manager, "SettingsDialog")
        }
    }

    private fun setTypeDataStorage() {
        typeDataStorage = settingsSharedPreferences.getSetting(applicationContext)
        textViewTypeStorage.text = typeDataStorage
        setData(typeDataStorage)
    }

    private fun setData(typeDataStorage: String?) {
        when (typeDataStorage) {
            resources.getString(R.string.externalDataStorage) -> textViewFile.text = externalDataStorage.readData(applicationContext)
            resources.getString(R.string.internalDataStorage) -> textViewFile.text = internalDataStorage.readData(applicationContext)
            else -> textViewFile.text = resources.getString(R.string.settingsTitle)
        }
    }

    override fun onResume() {
        super.onResume()
        setTypeDataStorage()
    }
}
