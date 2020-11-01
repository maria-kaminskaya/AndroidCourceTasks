package com.kmnvxh222.task8.model

data class WeatherResponse(
        val cod: Int,
        val message: Double,
        val cnt: Int,
        val list: List<ListWeather>,
        val city: City
) {
    data class ListWeather(
            val dt: Long,
            val main: Main,
            val weather: List<Weather>,
            val clouds: Clouds,
            val wind: Wind,
            val sys: Sys,
            val dt_txt: String
    ) {

        data class Main(
                val temp: Double,
                val temp_min: Double,
                val temp_max: Double,
                val pressure: Double,
                val sea_level: Double,
                val grnd_level: Double,
                val humidity: Int,
                val temp_kf: Double
        )

        data class Clouds(
                val all: Int
        )

        data class Wind(
                val speed: Double,
                val deg: Double
        )

        data class Sys(
                val pod: String
        )

        data class Weather(
                val id: Int,
                val main: String,
                val description: String,
                val icon: String
        )
    }

    data class City(
            val id: Int,
            val name: String,
            val coord: Coord,
            val country: String
    ) {
        data class Coord(
                val lat: Double,
                val lon: Double
        )
    }
}