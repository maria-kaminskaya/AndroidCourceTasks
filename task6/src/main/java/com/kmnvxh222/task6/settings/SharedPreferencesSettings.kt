package com.kmnvxh222.task6.settings

import android.content.Context
import android.content.SharedPreferences
import com.kmnvxh222.task6.R
import com.kmnvxh222.task6.async.RxJavaRepository
import com.kmnvxh222.task6.async.ThreadHandlerRepository
import com.kmnvxh222.task6.async.TreadCompletableRepository
import com.kmnvxh222.task6.db.DBHelper
import com.kmnvxh222.task6.db.DBInterface

class SharedPreferencesSettings {

    companion object {
        const val PREF_KEY = "TYPE_ASYNC_WORK_SETTING"
        const val SETTING_FILE_NAME = "SettingPreferences"
    }

    private var setting: String? = ""

    fun saveSetting(setting: String, context: Context) {
        val shared: SharedPreferences? = context.getSharedPreferences(SETTING_FILE_NAME, Context.MODE_PRIVATE)
        val edit: SharedPreferences.Editor? = shared?.edit()
        edit?.clear()
        edit?.putString(PREF_KEY, setting)
        edit?.apply()
    }

    fun getSetting(context: Context): String? {
        val shared: SharedPreferences? = context.getSharedPreferences(SETTING_FILE_NAME, Context.MODE_PRIVATE)
        setting = shared?.getString(PREF_KEY, "")
        return setting
    }

    fun asyncWork(context: Context, dbHelper: DBHelper): DBInterface? {
        val typeSettings = getSetting(context)
        var typeWork: DBInterface = ThreadHandlerRepository(dbHelper)
        when(typeSettings){
            context.getString(R.string.threadHandlerValue) -> typeWork = ThreadHandlerRepository(dbHelper)
            context.getString(R.string.threadCompletableValue)  -> typeWork = TreadCompletableRepository(dbHelper)
            context.getString(R.string.rxJavaValue) -> typeWork = RxJavaRepository(dbHelper)
            "" -> ThreadHandlerRepository(dbHelper)
        }
        return typeWork
    }

}