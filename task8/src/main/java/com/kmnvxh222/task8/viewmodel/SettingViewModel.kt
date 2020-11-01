package com.kmnvxh222.task8.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.kmnvxh222.task8.utils.SharedPreferencesSettings

class SettingViewModel(): ViewModel(){

    fun saveSetting(setting: Boolean, context: Context){
        SharedPreferencesSettings().saveSetting(setting,context)
    }
    fun getSetting(context: Context)=SharedPreferencesSettings().getSetting(context)

}