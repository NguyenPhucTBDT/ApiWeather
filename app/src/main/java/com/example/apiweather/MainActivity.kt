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
import androidx.recyclerview.widget.RecyclerView
import com.example.apiweather.adapter.WeatherAdapter
import com.example.apiweather.api.WeatherApiManager
import com.example.apiweather.model.WeatherView
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
        rcvWeather = findViewById(R.id.rcv_weather)
        callApi()
        initRecycleView()
    }

    @SuppressLint("NewApi")
    private fun callApi() {
        CoroutineScope(IO).launch {
            val info = WeatherApiManager.getInstance().getWeatherData()
            withContext(Main) {
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
    }

    private fun loadImage(icon: String) {
        val url = resources.getString(R.string.weather_icon_image_url, icon)
        imgIcon.loadImage(url)
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
            WeatherView.DaysWeather("Today", "18%", "", "", "35°C", "20°C"),
            WeatherView.DaysWeather("Today", "18%", "", "", "35°C", "20°C"),
            WeatherView.DaysWeather("Today", "18%", "", "", "35°C", "20°C"),
            WeatherView.DaysWeather("Today", "18%", "", "", "35°C", "20°C"),

        )

        val list = mutableListOf(
            WeatherView.TypeFourth(listSummaryWeather),
            WeatherView.TypeFifth(listSummaryWeatherDays),
            WeatherView.TypeOne("5:30 am", "7 pm"),
            WeatherView.TypeSecond("Extreme", "15 km/h", "80 %"),
            WeatherView.TypeThird("High", "Low", "None", "Good"),
        )
        rcvWeather.run {
            adapter = weatherAdapter
            isNestedScrollingEnabled = true
        }
        weatherAdapter.submitList(list)
    }

    companion object {
        const val IMAGE_WIDTH = 300
        const val IMAGE_HEIGHT = 300
    }
}
