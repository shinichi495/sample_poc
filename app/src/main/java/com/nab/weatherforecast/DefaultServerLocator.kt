package com.nab.weatherforecast

import android.app.Application
import android.content.Context
import com.nab.common.Logger
import com.nab.data.cache.RealmOpenWeatherDataSource
import com.nab.data.logger.AndroidLogger
import com.nab.data.openweather.GeneralErrorHandlerImpl
import com.nab.data.remote.RetrofitOpenWeatherDataSource
import com.nab.data.repo.WeatherRepository
import com.nab.domain.interfaces.ErrorHandler
import com.nab.domain.interfaces.IWeatherRepository
import com.nab.data.preference.KeystoreEncryptedLocalStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlin.reflect.KClass

class DefaultServerLocator private constructor(context: Context) : ServerLocator {
    companion object {
        private var instance: DefaultServerLocator? = null

        fun getInstance(application: Application) =
            instance ?: DefaultServerLocator(application).also {
                instance = it
            }
    }

    override val weatherRepository: IWeatherRepository by lazy {
        WeatherRepository(
            RetrofitOpenWeatherDataSource(),
            RealmOpenWeatherDataSource(),
            KeystoreEncryptedLocalStorage(
                context,
                getLogger(KeystoreEncryptedLocalStorage::class)
            )
        )
    }
    override val errorHandler: ErrorHandler by lazy { GeneralErrorHandlerImpl() }

    override val weatherDataSource by lazy { RetrofitOpenWeatherDataSource() }

    override val weatherHistoryDataSource by lazy { RealmOpenWeatherDataSource() }

    override val ioDipatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    override val mainDispatcher: CoroutineDispatcher
        get() = Dispatchers.Main.immediate

    private val logger = mutableMapOf<KClass<*>, AndroidLogger>()

    override fun getLogger(cls: KClass<*>): Logger = logger[cls] ?: AndroidLogger(cls).also {
        logger[cls] = it
    }
}