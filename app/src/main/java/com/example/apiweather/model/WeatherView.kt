package com.example.apiweather.model

sealed class WeatherView {
    data class TypeOne(
        val time_sunrise: String,
        val time_sunset: String
    ) : WeatherView()

    data class TypeSecond(
        val statusUV: String,
        val speedWind: String,
        val percent: String
    ) : WeatherView()

    data class TypeThird(
        val aqi: String,
        val pollen: String,
        val driving: String,
        val running: String
    ) : WeatherView()
}
