package com.kmnvxh222.task8.model

data class WeatherResponse(
        val lat : Double,
        val lon : Double,
        val timezone : String,
        val timezone_offset : Int,
        val current : Current,
        val hourly : List<Hourly>
) {
    data class Weather (
            val id : Int,
            val main : String,
            val description : String,
            val icon : String
    )

    data class Hourly (
            val dt : Int,
            val temp : Double,
            val feels_like : Double,
            val pressure : Int,
            val humidity : Int,
            val dew_point : Double,
            val clouds : Int,
            val visibility : Int,
            val wind_speed : Double,
            val wind_deg : Int,
            val weather : List<Weather>,
            val pop : Double
    )

    data class Current (
            val dt : Int,
            val sunrise : Int,
            val sunset : Int,
            val temp : Double,
            val feels_like : Double,
            val pressure : Int,
            val humidity : Int,
            val dew_point : Double,
            val uvi : Double,
            val clouds : Int,
            val visibility : Int,
            val wind_speed : Int,
            val wind_deg : Int,
            val weather : List<Weather>
    )
}