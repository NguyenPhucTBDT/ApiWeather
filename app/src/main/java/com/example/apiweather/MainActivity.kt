package com.example.apiweather

import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.icu.util.ULocale
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.apiweather.adapter.WeatherAdapter
import com.example.apiweather.model.DaysWeather
import com.example.apiweather.model.TimeWeather
import com.example.apiweather.model.WeatherResponse
import com.example.apiweather.model.WeatherView
import com.example.apiweather.mvp.WeatherContract
import com.example.apiweather.mvp.WeatherPresenter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

class MainActivity : AppCompatActivity(), WeatherContract.View {
    private val mPresenter: WeatherPresenter by lazy { WeatherPresenter() }
    private lateinit var tvTemp: TextView
    private lateinit var tvAddress: TextView
    private lateinit var imgIcon: ImageView
    private lateinit var tvTempMin: TextView
    private lateinit var tvTimeZone: TextView
    private lateinit var rcvWeather: RecyclerView
    private val weatherAdapter by lazy { WeatherAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvTemp = findViewById(R.id.txt_temp)
        tvAddress = findViewById(R.id.txt_address)
        imgIcon = findViewById(R.id.img_icon)
        tvTempMin = findViewById(R.id.tv_temp_max)
        tvTimeZone = findViewById(R.id.tv_time_zone)
        rcvWeather = findViewById(R.id.rcv_weather)
        initPresenter()
        mPresenter.getWeatherInfo()
        initRecycleView()
    }

    private fun loadImage(icon: String) {
        val url = resources.getString(R.string.weather_icon_image_url, icon)
        imgIcon.loadImage(url)
    }

    private fun initPresenter() {
        mPresenter.also {
            it.attachView(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.also {
            it.detachView()
        }
    }

    private fun initRecycleView() {
        val listSummaryWeather = mutableListOf(
            TimeWeather("6 am", "", "26°C", "10%"),
            TimeWeather("7 am", "", "26°C", "10%"),
            TimeWeather("8 am", "", "26°C", "10%"),
            TimeWeather("9 am", "", "26°C", "10%"),
            TimeWeather("10 am", "", "26°C", "10%"),
            TimeWeather("11 am", "", "26°C", "10%"),
            TimeWeather("12 am", "", "26°C", "10%"),
            TimeWeather("13 pm", "", "26°C", "10%"),
        )

        val listSummaryWeatherDays = mutableListOf(
            DaysWeather("Today", "18%", "", "", "35°C", "20°C"),
            DaysWeather("Today", "18%", "", "", "35°C", "20°C"),
            DaysWeather("Today", "18%", "", "", "35°C", "20°C"),
            DaysWeather("Today", "18%", "", "", "35°C", "20°C"),
            DaysWeather("Today", "18%", "", "", "35°C", "20°C"),
            DaysWeather("Today", "18%", "", "", "35°C", "20°C"),
            DaysWeather("Today", "18%", "", "", "35°C", "20°C"),
            DaysWeather("Today", "18%", "", "", "35°C", "20°C"),
            DaysWeather("Today", "18%", "", "", "35°C", "20°C"),
            DaysWeather("Today", "18%", "", "", "35°C", "20°C"),
        )

        val list = mutableListOf(
            WeatherView.TypeFifth(
                WeatherAdapter.WeatherViewTYPE.FIFTH.ordinal.toString(),
                listSummaryWeather
            ),
            WeatherView.TypeFourth(
                WeatherAdapter.WeatherViewTYPE.FOURTH.ordinal.toString(),
                listSummaryWeatherDays
            ),
            WeatherView.TypeOne(
                WeatherAdapter.WeatherViewTYPE.ONE.ordinal.toString(),
                "5:30 am",
                "7 pm"
            ),
            WeatherView.TypeSecond(
                WeatherAdapter.WeatherViewTYPE.SECOND.ordinal.toString(),
                "Extreme",
                "15 km/h",
                "80 %"
            ),
            WeatherView.TypeThird(
                WeatherAdapter.WeatherViewTYPE.THIRD.ordinal.toString(),
                "High",
                "Low",
                "None",
                "Good"
            ),
        )
        rcvWeather.run {
            adapter = weatherAdapter
        }
        weatherAdapter.submitList(list)
    }

    companion object {
        const val IMAGE_WIDTH = 300
        const val IMAGE_HEIGHT = 300
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun showWeatherInfo(info: WeatherResponse) {
        val fmtFr =
            MeasureFormat.getInstance(ULocale.ENGLISH, MeasureFormat.FormatWidth.SHORT)
        val measure =
            Measure(ceil(requireNotNull(info.main?.temp)), MeasureUnit.CELSIUS)
        tvTemp.text = fmtFr.format(measure)
        tvAddress.text = info.name.toString()
        loadImage(info.weather?.get(0)?.icon.toString())
        val stringTemp =
            ceil(requireNotNull(info.main?.temp_max)).toString() + "°C" + " / " + ceil(
                requireNotNull(info.main?.temp_min)
            ).toString() + "°C " + " Feels like " + ceil(
                requireNotNull(info.main?.feels_like)
            ).toString() + "°C"
        tvTempMin.text = stringTemp
        val dateFormat =
            SimpleDateFormat("EEEE , HH:mm a", Locale(info.timezone.toString()))
        tvTimeZone.text = dateFormat.format(Date())
    }
}
