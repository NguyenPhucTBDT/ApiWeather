package com.example.apiweather.mvp

import com.example.apiweather.model.WeatherResponse

class WeatherContract {
    interface View {
        fun showWeatherInfo(info: WeatherResponse)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun getWeatherInfo()
    }
}
