package com.example.apiweather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apiweather.R
import com.example.apiweather.model.WeatherView

class WeatherAdapter : ListAdapter<WeatherView, RecyclerView.ViewHolder>(WeatherDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            WeatherViewTYPE.ONE.ordinal -> ViewHolderOne.create(parent)
            WeatherViewTYPE.SECOND.ordinal -> ViewHolderTwo.create(parent)
            WeatherViewTYPE.THIRD.ordinal -> ViewHolderThird.create(parent)
            WeatherViewTYPE.FOURTH.ordinal -> ViewHolderFourth.create(parent)
            else -> ViewHolderFifth.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            WeatherViewTYPE.ONE.ordinal -> (holder as ViewHolderOne).bind(getItem(position) as WeatherView.TypeOne)
            WeatherViewTYPE.SECOND.ordinal -> (holder as ViewHolderTwo).bind(getItem(position) as WeatherView.TypeSecond)
            WeatherViewTYPE.THIRD.ordinal -> (holder as ViewHolderThird).bind(getItem(position) as WeatherView.TypeThird)
            WeatherViewTYPE.FOURTH.ordinal -> (holder as ViewHolderFourth).bind(getItem(position) as WeatherView.TypeFourth)
            WeatherViewTYPE.FIFTH.ordinal -> (holder as ViewHolderFifth).bind(getItem(position) as WeatherView.TypeFifth)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is WeatherView.TypeOne -> WeatherViewTYPE.ONE.ordinal
            is WeatherView.TypeSecond -> WeatherViewTYPE.SECOND.ordinal
            is WeatherView.TypeThird -> WeatherViewTYPE.THIRD.ordinal
            is WeatherView.TypeFourth -> WeatherViewTYPE.FOURTH.ordinal
            else -> WeatherViewTYPE.FIFTH.ordinal
        }
    }

    class ViewHolderOne(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var tvTimeSunrise: TextView
        private lateinit var tvTimeSunset: TextView
        private fun initView() {
            tvTimeSunrise = itemView.findViewById(R.id.txt_time_sunrise)
            tvTimeSunset = itemView.findViewById(R.id.txt_time_sunset)
        }

        fun bind(data: WeatherView.TypeOne) {
            initView()
            tvTimeSunset.text = data.time_sunset
            tvTimeSunrise.text = data.time_sunrise
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolderOne {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_type_one, parent, false)
                return ViewHolderOne(view)
            }
        }
    }

    class ViewHolderTwo(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var tvUVIndex: TextView
        private lateinit var tvWindSpeed: TextView
        private lateinit var tvPercent: TextView
        private fun initView() {
            tvUVIndex = itemView.findViewById(R.id.txt_uv_index)
            tvWindSpeed = itemView.findViewById(R.id.txt_wind_speed)
            tvPercent = itemView.findViewById(R.id.txt_percent_humidity)
        }

        fun bind(data: WeatherView.TypeSecond) {
            initView()
            tvUVIndex.text = data.statusUV
            tvWindSpeed.text = data.speedWind
            tvPercent.text = data.percent
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolderTwo {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_type_second, parent, false)
                return ViewHolderTwo(view)
            }
        }
    }

    class ViewHolderThird(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var tvAQI: TextView
        private lateinit var tvPollen: TextView
        private lateinit var tvDriving: TextView
        private lateinit var tvRunning: TextView
        private fun initView() {
            tvAQI = itemView.findViewById(R.id.txt_aqi)
            tvPollen = itemView.findViewById(R.id.txt_pollen)
            tvDriving = itemView.findViewById(R.id.txt_driving_difficulty)
            tvRunning = itemView.findViewById(R.id.txt_running)
        }

        fun bind(data: WeatherView.TypeThird) {
            initView()
            tvAQI.text = data.aqi
            tvDriving.text = data.driving
            tvPollen.text = data.pollen
            tvRunning.text = data.running
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolderThird {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_type_third, parent, false)
                return ViewHolderThird(view)
            }
        }
    }

    class ViewHolderFourth(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var rcvTimeWeather: RecyclerView
        private val daysAdapter by lazy { SummaryWeatherDaysAdapter() }
        private fun initView() {
            rcvTimeWeather = itemView.findViewById(R.id.rcv_days_weather)
            rcvTimeWeather.run {
                adapter = daysAdapter
            }
        }

        fun bind(data: WeatherView.TypeFourth) {
            initView()
            daysAdapter.submitList(data.list)
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolderFourth {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_layout_type_fourth, parent, false)
                return ViewHolderFourth(view)
            }
        }
    }

    class ViewHolderFifth(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var rcvDaysWeather: RecyclerView
        private val timeWeather by lazy { SummaryWeatherAdapter() }
        private fun initView() {
            rcvDaysWeather = itemView.findViewById(R.id.rcv_time_weather)
            rcvDaysWeather.run {
                adapter = timeWeather
            }
        }

        fun bind(data: WeatherView.TypeFifth) {
            initView()
            timeWeather.submitList(data.list)
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolderFifth {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_layout_type_fifth, parent, false)
                return ViewHolderFifth(view)
            }
        }
    }

    class WeatherDiffCallback : DiffUtil.ItemCallback<WeatherView>() {
        override fun areItemsTheSame(
            oldItem: WeatherView,
            newItem: WeatherView
        ) = oldItem::class.java.simpleName == newItem::class.java.simpleName

        override fun areContentsTheSame(
            oldItem: WeatherView,
            newItem: WeatherView
        ) = oldItem.id == newItem.id
    }

    enum class WeatherViewTYPE {
        ONE,
        SECOND,
        THIRD,
        FOURTH,
        FIFTH
    }
}
