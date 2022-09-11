package com.nab.domain.usecases

import com.nab.domain.base.UseCase
import com.nab.domain.interfaces.IWeatherRepository
import com.nab.domain.models.GetWeatherResult
import kotlinx.coroutines.flow.Flow

typealias WeatherBaseUseCase = UseCase<String, GetWeatherResult>

class WeatherUseCase(private val repository: IWeatherRepository) :
    WeatherBaseUseCase {
    override suspend fun run(params: String): Flow<GetWeatherResult> = repository.getWeathersByQuery(q = params)

}