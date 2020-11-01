package com.kmnvxh222.task8.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesSettings {

    companion object {
        const val PREF_KEY = "GRADE_TYPE_SETTING"
        const val SETTING_FILE_NAME = "SettingPreferences"
    }

    private var setting: Boolean? = null

    fun saveSetting(setting: Boolean, context: Context) {
        val shared: SharedPreferences? = context.getSharedPreferences(SETTING_FILE_NAME, Context.MODE_PRIVATE)
        val edit: SharedPreferences.Editor? = shared?.edit()
        edit?.clear()
        edit?.putBoolean(PREF_KEY, setting)
        edit?.apply()
    }

    fun getSetting(context: Context): Boolean? {
        val shared: SharedPreferences? = context.getSharedPreferences(SETTING_FILE_NAME, Context.MODE_PRIVATE)
        setting = shared?.getBoolean(PREF_KEY, true)
        return setting
    }

}