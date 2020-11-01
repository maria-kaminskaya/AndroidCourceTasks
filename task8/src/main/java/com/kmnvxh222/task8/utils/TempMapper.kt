package com.kmnvxh222.task8.utils

import java.text.DecimalFormat

class TempMapper() {

    fun convertTemp(setting: Boolean?, tempKel: Double): String {
        return when (setting) {
            true -> kelvinToCelsius(tempKel)
            null -> kelvinToCelsius(tempKel)
            false -> kelvinToFahrenheit(tempKel)
        }
    }

    private fun kelvinToFahrenheit(tempKel: Double): String {
        val decimalFormat = DecimalFormat("#.#")
        val temp = decimalFormat.format(tempKel * 1.8 - 459.67)
        return "$temp 'F"
    }

    private fun kelvinToCelsius(tempKel: Double): String {
        val decimalFormat = DecimalFormat("#.#")
        val temp = decimalFormat.format(tempKel - 273.15)
        return "$temp 'C"
    }

}

//Пересчёт температуры между основными шкалами
//Кельвин (K) = K = С + 273,15
//Цельсий (°C) = K − 273,15	= C
//Фаренгейт (°F) = K · 1,8 − 459,67	= C · 1,8 + 32
