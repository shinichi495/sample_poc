package com.nab.weatherforecast.mock

import com.nab.domain.models.GetWeatherResult
import com.nab.domain.usecases.WeatherBaseUseCase
import com.nab.weatherforecast.base.BaseTestUseCase
import com.nab.weatherforecast.util.State
import kotlinx.coroutines.flow.Flow

class FakeWeatherUseCase(state: State) : BaseTestUseCase<String, GetWeatherResult>(state),
    WeatherBaseUseCase {

    override fun getValue(params: String): GetWeatherResult = Data.weatherResult

    override suspend fun run(params: String): Flow<GetWeatherResult> = excute(params)

}