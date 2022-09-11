package com.nab.weatherforecast

import com.nab.common.Logger
import com.nab.domain.interfaces.ErrorHandler
import com.nab.domain.interfaces.GetWeatherDataSource
import com.nab.domain.interfaces.GetWeatherHistoryDataSource
import com.nab.domain.interfaces.IWeatherRepository
import com.nab.data.preference.KeystoreEncryptedLocalStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlin.reflect.KClass

interface ServerLocator {

    val weatherRepository : IWeatherRepository

    val errorHandler : ErrorHandler

    val weatherDataSource : GetWeatherDataSource

    val weatherHistoryDataSource : GetWeatherHistoryDataSource

    val ioDipatcher : CoroutineDispatcher

    val mainDispatcher : CoroutineDispatcher

    fun getLogger (cls : KClass<*>) : Logger

}