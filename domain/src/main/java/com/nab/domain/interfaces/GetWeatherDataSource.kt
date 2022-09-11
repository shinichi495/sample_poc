package com.nab.domain.interfaces

import com.nab.domain.models.GetWeatherResult

interface GetWeatherDataSource {
    suspend fun getWeather(q: String, appId: String): GetWeatherResult
    suspend fun getAppId() : String
}