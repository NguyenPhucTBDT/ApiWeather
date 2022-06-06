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


class SummaryWeatherDaysAdapter :
    ListAdapter<WeatherView.DaysWeather, SummaryWeatherDaysAdapter.ViewHolder>(SummaryWeatherDaysDiffCallback()) {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDay = itemView.findViewById<TextView>(R.id.txt_day)
        private val tvMoisture = itemView.findViewById<TextView>(R.id.txt_moisture_day)
        private val tvTempMax = itemView.findViewById<TextView>(R.id.txt_temp_max)
        private val tvTempMin = itemView.findViewById<TextView>(R.id.txt_temp_min)
        fun bind(item: WeatherView.DaysWeather) {
            tvDay.text = item.days
            tvMoisture.text = item.moisture
            tvTempMax.text = item.temp_max
            tvTempMin.text = item.temp_min
        }
    }

    class SummaryWeatherDaysDiffCallback : DiffUtil.ItemCallback<WeatherView.DaysWeather>() {
        override fun areItemsTheSame(oldItem: WeatherView.DaysWeather, newItem: WeatherView.DaysWeather): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WeatherView.DaysWeather, newItem: WeatherView.DaysWeather): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_weather_days, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}