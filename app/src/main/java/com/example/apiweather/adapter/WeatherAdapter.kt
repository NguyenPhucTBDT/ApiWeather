package com.example.apiweather.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apiweather.R
import com.example.apiweather.model.WeatherView

class WeatherAdapter : ListAdapter<WeatherView, WeatherAdapter.ViewHolder>(WeatherDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = when (viewType) {
            0 -> R.layout.item_type_one
            1 -> R.layout.item_type_second
            2 -> R.layout.item_type_third
            3 -> R.layout.item_layout_type_fifth
            else -> R.layout.item_layout_type_fourth
        }
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val v = inflater.inflate(layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is WeatherView.TypeOne -> 0
            is WeatherView.TypeSecond -> 1
            is WeatherView.TypeThird -> 2
            is WeatherView.TypeFourth -> 3
            else -> 4
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTimeSunrise = itemView.findViewById<TextView>(R.id.txt_time_sunrise)
        private val tvTimeSunset = itemView.findViewById<TextView>(R.id.txt_time_sunset)
        private val tvUVIndex = itemView.findViewById<TextView>(R.id.txt_uv_index)
        private val tvWindSpeed = itemView.findViewById<TextView>(R.id.txt_wind_speed)
        private val tvPercent = itemView.findViewById<TextView>(R.id.txt_percent_humidity)
        private val tvAQI = itemView.findViewById<TextView>(R.id.txt_aqi)
        private val tvPollen = itemView.findViewById<TextView>(R.id.txt_pollen)
        private val tvDriving = itemView.findViewById<TextView>(R.id.txt_driving_difficulty)
        private val tvRunning = itemView.findViewById<TextView>(R.id.txt_running)
        private val rcvTimeWeather = itemView.findViewById<RecyclerView>(R.id.rcv_time_weather)
        private val rcvDaysWeather = itemView.findViewById<RecyclerView>(R.id.rcv_days_weather)
        private fun bindTypeOne(item: WeatherView.TypeOne) {
            tvTimeSunrise.text = item.time_sunrise
            tvTimeSunset.text = item.time_sunset
        }

        private fun bindTypeSecond(item: WeatherView.TypeSecond) {
            tvUVIndex.text = item.statusUV
            tvWindSpeed.text = item.speedWind
            tvPercent.text = item.percent
        }

        private fun bindTypeThird(item: WeatherView.TypeThird) {
            tvAQI.text = item.aqi
            tvDriving.text = item.driving
            tvPollen.text = item.pollen
            tvRunning.text = item.running
        }

        private fun bindTimeWeather(item: WeatherView.TypeFourth) {
            val timeAdapter = SummaryWeatherAdapter()
            rcvTimeWeather.run {
                adapter = timeAdapter
            }
            timeAdapter.submitList(item.list)
        }

        private fun bindDaysWeather(item: WeatherView.TypeFifth) {
            val daysAdapter = SummaryWeatherDaysAdapter()
            rcvDaysWeather.run {
                adapter = daysAdapter
            }
            daysAdapter.submitList(item.list)
        }

        fun bind(dataModel: WeatherView) {
            when (dataModel) {
                is WeatherView.TypeOne -> bindTypeOne(dataModel)
                is WeatherView.TypeSecond -> bindTypeSecond(dataModel)
                is WeatherView.TypeThird -> bindTypeThird(dataModel)
                is WeatherView.TypeFourth -> bindTimeWeather(dataModel)
                is WeatherView.TypeFifth -> bindDaysWeather(dataModel)
            }
        }
    }

    class WeatherDiffCallback : DiffUtil.ItemCallback<WeatherView>() {
        override fun areItemsTheSame(
            oldItem: WeatherView,
            newItem: WeatherView
        ): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: WeatherView,
            newItem: WeatherView
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
