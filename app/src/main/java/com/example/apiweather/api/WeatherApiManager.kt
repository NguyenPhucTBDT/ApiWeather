package com.example.apiweather.api

import com.example.apiweather.model.WeatherResponse

class WeatherApiManager {
    private val apiHelper = ApiHelper.getInstance().create(WeatherService::class.java)
    suspend fun getWeatherData(): WeatherResponse {
        val response = apiHelper.getCurrentWeatherInfo(LAT, LON, LANG, UNITS, APP_ID)
        if (response.isSuccessful && response.body() != null) {
            return response.body() as WeatherResponse
        }
        return response.body() as WeatherResponse
    }

    companion object {
        fun getInstance() = WeatherApiManager()
        const val LAT = "21.0278"
        const val LON = "105.8342"
        const val LANG = "vi"
        const val UNITS = "metric"
        const val APP_ID = "f2531f441e88f4c97950a7e95a594fa8"
    }
}
