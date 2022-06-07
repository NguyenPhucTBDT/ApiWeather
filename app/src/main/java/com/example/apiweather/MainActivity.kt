package com.example.apiweather

import android.annotation.SuppressLint
import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.icu.util.ULocale
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apiweather.adapter.SummaryWeatherAdapter
import com.example.apiweather.adapter.SummaryWeatherDaysAdapter
import com.example.apiweather.adapter.WeatherAdapter
import com.example.apiweather.api.ApiHelper
import com.example.apiweather.api.WeatherService
import com.example.apiweather.model.WeatherView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
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
        callApi()
        initRecycleView()
    }

    @SuppressLint("NewApi")
    private fun callApi() {
        val api = ApiHelper.getInstance().create(WeatherService::class.java)
        CoroutineScope(IO).launch {
            val weatherData = api.getCurrentWeatherInfo(LAT, LON, LANG, UNITS, APP_ID)
            if (weatherData.isSuccessful && weatherData.body() != null) {
                val info = weatherData.body()!!
                withContext(Main) {
                    val fmtFr =
                        MeasureFormat.getInstance(ULocale.ENGLISH, MeasureFormat.FormatWidth.SHORT)
                    val measure =
                        Measure(ceil(info.main?.temp!!), MeasureUnit.CELSIUS);
                    tvTemp.text = fmtFr.format(measure)
                    tvAddress.text = info.name.toString()
                    withContext(Main) {
                        loadImage(info.weather?.get(0)?.icon.toString())
                    }
                    val stringTemp =
                        ceil(info.main?.temp_max!!).toString() + "°C" + " / " + ceil(info.main?.temp_min!!).toString() + "°C " + " Feels like " + ceil(
                            info.main?.feels_like!!
                        ).toString() + "°C"
                    tvTempMin.text = stringTemp
                    val dateFormat =
                        SimpleDateFormat("EEEE , HH:mm a", Locale(info.timezone.toString()))
                    tvTimeZone.text = dateFormat.format(Date())
                }
            }
        }
    }

    private fun loadImage(icon: String) {
        val url = resources.getString(R.string.weather_icon_image_url, icon)
        Picasso.get().load(url)
            .error(R.drawable.ic_launcher_foreground)
            .resize(IMAGE_WIDTH, IMAGE_HEIGHT)
            .centerCrop()
            .into(imgIcon)
    }

    private fun initRecycleView() {
        val listSummaryWeather = mutableListOf(
            WeatherView.TimeWeather("6 am", "", "26°C", "10%"),
            WeatherView.TimeWeather("7 am", "", "26°C", "10%"),
            WeatherView.TimeWeather("8 am", "", "26°C", "10%"),
            WeatherView.TimeWeather("9 am", "", "26°C", "10%"),
            WeatherView.TimeWeather("10 am", "", "26°C", "10%"),
            WeatherView.TimeWeather("11 am", "", "26°C", "10%"),
            WeatherView.TimeWeather("12 am", "", "26°C", "10%"),
            WeatherView.TimeWeather("13 pm", "", "26°C", "10%"),
        )

        val listSummaryWeatherDays = mutableListOf(
            WeatherView.DaysWeather("Today", "18%", "", "", "35°C", "20°C"),
            WeatherView.DaysWeather("Today", "18%", "", "", "35°C", "20°C"),
            WeatherView.DaysWeather("Today", "18%", "", "", "35°C", "20°C"),
            WeatherView.DaysWeather("Today", "18%", "", "", "35°C", "20°C"),
            WeatherView.DaysWeather("Today", "18%", "", "", "35°C", "20°C"),
            WeatherView.DaysWeather("Today", "18%", "", "", "35°C", "20°C"),
            WeatherView.DaysWeather("Today", "18%", "", "", "35°C", "20°C")
        )

        val list = mutableListOf(
            WeatherView.TypeFourth(listSummaryWeather),
            WeatherView.TypeFifth(listSummaryWeatherDays),
            WeatherView.TypeOne("5:30 am", "7 pm"),
            WeatherView.TypeSecond("Extreme", "15 km/h", "80 %"),
            WeatherView.TypeThird("Hight", "Low", "None", "Good"),
        )
        rcvWeather.run {
            adapter = weatherAdapter
        }
        weatherAdapter.submitList(list)
    }


    companion object {
        const val LAT = "21.0278"
        const val LON = "105.8342"
        const val LANG = "vi"
        const val UNITS = "metric"
        const val APP_ID = "f2531f441e88f4c97950a7e95a594fa8"
        const val IMAGE_WIDTH = 300
        const val IMAGE_HEIGHT = 300
    }
}
