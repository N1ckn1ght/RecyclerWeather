package com.example.recyclerweather

// https://github.com/captaincod/CurrentWeatherDataBinding/blob/main/app/src/main/java/com/example/currentweatherdatabinding/MainActivity.kt
data class WeatherJSON(val coord: Coord, val weather: Array<WeatherArray>, val base: String,
                       val main: WeatherMain, val visibility: Long, val wind: WeatherWind,
                       val clouds: WeatherClouds, val dt: Long, val sys: WeatherSys,
                       val timezone: Long, val id: Long, val name: String, val cod: Int)
data class Coord(val lon: Double, val lat: Double)
data class WeatherArray(val id: Int, val main: String, val description: String, val icon: String)
data class WeatherMain(val temp: String, val feels_like: Double,
                       val temp_min: Double, val temp_max: Double,
                       val pressure: Int, val humidity: Int,
                       val gust: Double = 0.0, val sea_level: Int = 0, val grnd_level:Int = 0)
data class WeatherWind(val speed: String, val deg: Int,
                       val gust: Double = 0.0)
data class WeatherClouds(val all: Int)
data class WeatherSys(val type: Int, val id: Int, val country: String, val sunrise: Long, val sunset: Long)