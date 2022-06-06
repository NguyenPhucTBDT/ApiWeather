package com.example.apiweather.api

import com.example.apiweather.model.WeatherRespone
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("/data/2.5/weather?")
    suspend fun getCurrentWeatherInfor(@Query("lat") lat: String, @Query("lon") lon: String,@Query("lang") lang:String,@Query("units") units:String,@Query("APPID") app_id: String): Response<WeatherRespone>
}