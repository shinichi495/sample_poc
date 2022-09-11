package com.nab.weatherforecast.mock

import com.nab.domain.models.GetWeatherResult

object Data {
    val weather = GetWeatherResult.Weather("Sat, 10/09/2022"
        , 26.65
        , 1013
        , 82
        , "light rain")
    val weathers = mutableListOf<GetWeatherResult.Weather>(weather)
    val weatherResult = GetWeatherResult(query = "saigon", weathers)

}