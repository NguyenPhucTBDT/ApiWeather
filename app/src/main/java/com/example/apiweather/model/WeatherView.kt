package com.example.apiweather.model

sealed class WeatherView(val id: String) {
    class TypeOne(
        id: String,
        val time_sunrise: String,
        val time_sunset: String
    ) : WeatherView(id)

    class TypeSecond(
        id: String,
        val statusUV: String,
        val speedWind: String,
        val percent: String
    ) : WeatherView(id)

    class TypeThird(
        id: String,
        val aqi: String,
        val pollen: String,
        val driving: String,
        val running: String
    ) : WeatherView(id)

    class TypeFourth(
        id: String,
        val list: MutableList<DaysWeather>
    ) : WeatherView(id)

    class TypeFifth(
        id: String,
        val list: MutableList<TimeWeather>
    ) : WeatherView(id)
}
