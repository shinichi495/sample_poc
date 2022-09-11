package com.nab.data.remote

import com.nab.data.entity.response.weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface OpenWeatherService {
    @GET("data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("cnt") cnt: Int,
        @Query("appid") appid: String
    ): WeatherResponse
}