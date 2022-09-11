package com.nab.domain.interfaces

import com.nab.domain.models.GetWeatherResult
import kotlinx.coroutines.flow.Flow

interface IWeatherRepository {
    suspend fun getWeathersByQuery(q: String): Flow<GetWeatherResult>
    suspend fun fetchAppId () : Flow<String>
}