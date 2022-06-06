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

    data class TimeWeather(
        val time: String,
        val icon: String,
        val temp: String,
        val moisture: String
    )

    data class DaysWeather(
        val days: String,
        val moisture: String,
        val icon1: String,
        val icon2: String,
        val temp_max: String,
        val temp_min: String,
    )
}
