package com.kmnvxh222.task8.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(context: Context): ViewModelProvider.Factory {

    private val contextInner = context
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(contextInner) as T
    }
}