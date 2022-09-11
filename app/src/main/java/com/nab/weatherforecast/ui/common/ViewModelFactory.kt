package com.nab.weatherforecast.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nab.domain.usecases.GetMockApiKeyUseCase
import com.nab.domain.usecases.WeatherUseCase
import com.nab.weatherforecast.ServerLocator
import com.nab.weatherforecast.ui.weather.WeatherViewModel

class ViewModelFactory(private val serverLocator: ServerLocator) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(WeatherViewModel::class.java) -> {
                    WeatherViewModel(
                        WeatherUseCase(serverLocator.weatherRepository),
                        GetMockApiKeyUseCase(serverLocator.weatherRepository),
                        serverLocator.getLogger(WeatherViewModel::class),
                        serverLocator.errorHandler
                    )
                }
                else -> throw IllegalArgumentException("unknow class ${modelClass.canonicalName}")
            }
        } as T

}