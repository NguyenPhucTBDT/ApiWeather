package com.example.apiweather

import android.annotation.SuppressLint
import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.icu.util.ULocale
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apiweather.adapter.SummaryWeatherAdapter
import com.example.apiweather.adapter.SummaryWeatherDaysAdapter
import com.example.apiweather.adapter.WeatherAdapter
import com.example.apiweather.api.ApiHelper
import com.example.apiweather.api.WeatherService
import com.example.apiweather.model.DaysWeather
import com.example.apiweather.model.TimeWeather
import com.example.apiweather.model.WeatherView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    var tvTemp: TextView? = null
    var tvAddress: TextView? = null
    var imgIcon: ImageView? = null
    var tvTempMin: TextView? = null
    var tvTimeZone: TextView? = null
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
            val weatherData = api.getCurrentWeatherInfor(lat, lon, lang, units, appId)
            if (weatherData.isSuccessful && weatherData.body() != null) {
                val infor = weatherData.body()!!
                withContext(Main) {
                    val fmtFr =
                        MeasureFormat.getInstance(ULocale.ENGLISH, MeasureFormat.FormatWidth.SHORT)
                    val measure =
                        Measure(ceil(infor.main?.temp!!), MeasureUnit.CELSIUS);
                    tvTemp?.text = fmtFr.format(measure)
                    tvAddress?.text = infor.name.toString()
                    withContext(Main) {
                        loadImage(infor.weather?.get(0)?.icon.toString())
                    }
                    val stringTemp =
                        ceil(infor.main?.temp_max!!).toString() + "°C" + " / " + ceil(infor.main?.temp_min!!).toString() + "°C " + " Feels like " + ceil(
                            infor.main?.feels_like!!
                        ).toString() + "°C"
                    tvTempMin?.text = stringTemp
                    val dateFormat =
                        SimpleDateFormat("EEEE , HH:mm a", Locale(infor.timezone.toString()))
                    tvTimeZone?.text = dateFormat.format(Date())
                }
            }
        }
    }

    private fun loadImage(icon: String) {
        val url = "https://openweathermap.org/img/wn/$icon@2x.png"
        Picasso.get().load(url)
            .error(R.drawable.ic_launcher_foreground)
            .resize(300, 300)
            .centerCrop()
            .into(imgIcon)
    }

    private fun initRecycleView() {
        val listSummaryWeather = mutableListOf<TimeWeather>(
            TimeWeather("6 am", "", "26°C", "10%"),
            TimeWeather("7 am", "", "26°C", "10%"),
            TimeWeather("8 am", "", "26°C", "10%"),
            TimeWeather("9 am", "", "26°C", "10%"),
            TimeWeather("10 am", "", "26°C", "10%"),
            TimeWeather("11 am", "", "26°C", "10%"),
            TimeWeather("12 am", "", "26°C", "10%"),
            TimeWeather("13 pm", "", "26°C", "10%"),
        )
        val rcvSummary = findViewById<RecyclerView>(R.id.rcv_summary_weather)
        val summaryAdapter = SummaryWeatherAdapter()
        summaryAdapter.submitList(listSummaryWeather)
        rcvSummary.adapter = summaryAdapter
        rcvSummary.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        //
        val listSummaryWeatherDays = mutableListOf<DaysWeather>(
            DaysWeather("Today","18%","","","35°C","20°C"),
            DaysWeather("Today","18%","","","35°C","20°C"),
            DaysWeather("Today","18%","","","35°C","20°C"),
            DaysWeather("Today","18%","","","35°C","20°C"),
            DaysWeather("Today","18%","","","35°C","20°C"),
            DaysWeather("Today","18%","","","35°C","20°C"),
            DaysWeather("Today","18%","","","35°C","20°C")
        )
        val rcvSummaryDays = findViewById<RecyclerView>(R.id.rcv_summary_weather_days)
        val summaryDaysAdapter = SummaryWeatherDaysAdapter()
        summaryDaysAdapter.submitList(listSummaryWeatherDays)
        rcvSummaryDays.adapter = summaryDaysAdapter
        rcvSummaryDays.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //
        val list = mutableListOf<WeatherView>(
            WeatherView.TypeOne("5:30 am", "7 pm"),
            WeatherView.TypeSecond("Extreme", "15 km/h", "80 %"),
            WeatherView.TypeThird("Hight", "Low", "None", "Good"),
        )
        val rcvWeather = findViewById<RecyclerView>(R.id.rcv_weather)
        val weatherAdapter = WeatherAdapter()
        weatherAdapter.submitList(list)
        rcvWeather.adapter = weatherAdapter
        rcvWeather.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcvWeather.isNestedScrollingEnabled = false
    }


    companion object {
        var lat = "21.0278"
        var lon = "105.8342"
        var lang = "vi"
        var units = "metric"
        var appId = "f2531f441e88f4c97950a7e95a594fa8"
    }
}
