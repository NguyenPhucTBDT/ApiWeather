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

class SummaryWeatherAdapter :
    ListAdapter<WeatherView.TimeWeather, SummaryWeatherAdapter.ViewHolder>(
        SummaryWeatherDiffCallback()
    ) {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTime = itemView.findViewById<TextView>(R.id.txt_time)
        private val tvTemp = itemView.findViewById<TextView>(R.id.txt_temp)
        private val tvMoisture = itemView.findViewById<TextView>(R.id.txt_moisture)
        fun bind(item: WeatherView.TimeWeather) {
            tvTime.text = item.time
            tvTemp.text = item.temp
            tvMoisture.text = item.moisture
        }
    }

    class SummaryWeatherDiffCallback : DiffUtil.ItemCallback<WeatherView.TimeWeather>() {
        override fun areItemsTheSame(
            oldItem: WeatherView.TimeWeather,
            newItem: WeatherView.TimeWeather
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: WeatherView.TimeWeather,
            newItem: WeatherView.TimeWeather
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
