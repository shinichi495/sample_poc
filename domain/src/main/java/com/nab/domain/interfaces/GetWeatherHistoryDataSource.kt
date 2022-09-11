package com.nab.domain.interfaces

import com.nab.domain.models.GetWeatherResult

interface GetWeatherHistoryDataSource {
    suspend fun getInCache(q: String): GetWeatherResult
    suspend fun addInCache(weatherResult: GetWeatherResult)
}