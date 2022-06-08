package com.example.apiweather.mvp

import com.example.apiweather.api.WeatherApiManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherPresenter : WeatherContract.Presenter {
    private var mView: WeatherContract.View? = null
    override fun attachView(view: WeatherContract.View) {
        this.mView = view
    }

    override fun detachView() {
        this.mView = null
    }

    override fun getWeatherInfo() {
        CoroutineScope(IO).launch {
            val info = WeatherApiManager.getInstance().getWeatherData()
            withContext(Main) {
                mView?.showWeatherInfo(info)
            }
        }
    }
}
