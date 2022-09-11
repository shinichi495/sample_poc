package com.nab.data.repo

import com.nab.data.preference.KeystoreEncryptedLocalStorage
import com.nab.data.preference.LocalPreferencesKeys
import com.nab.domain.interfaces.GetWeatherDataSource
import com.nab.domain.interfaces.GetWeatherHistoryDataSource
import com.nab.domain.interfaces.IWeatherRepository
import com.nab.domain.models.GetWeatherResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.InvalidObjectException

class WeatherRepository(
    val remote: GetWeatherDataSource,
    val cache: GetWeatherHistoryDataSource,
    val pref: KeystoreEncryptedLocalStorage
) : IWeatherRepository {
    override suspend fun getWeathersByQuery(q: String): Flow<GetWeatherResult> = flow {
        if (q.length < 3) {
            throw InvalidObjectException("Input is not correcet")
        } else {
            val local = cache.getInCache(q)
            if (local.weathers?.size != 0) emit(local) else {
                val remotes = remote.getWeather(q, getAppIdFromApp())
                if (remotes.weathers?.size != 0) cache.addInCache(remotes)
                emit(remotes)
            }
        }

    }

    /*
   - on this case we will mock a use case for Encrypt scenario
   - when user access our app :
       - we will check in local store (KeystoreEncryptedLocalStorage.kt) (1)
           - if the local store do not have appId we will call mock api to to get appID (1.1)
               - when get appId success , we will encrypt this appId and store it in local store (1.1.1)
           - if the local store have appId, we will decrypt it from local (1.2)
   * */

    override suspend fun fetchAppId(): Flow<String> = flow {
        var appId = getAppIdFromApp()
        if (appId.isNullOrBlank()) appId = getAppIdFromRemote()
        emit(appId)
    }

    private fun getAppIdFromApp(): String {
        return pref.getString(LocalPreferencesKeys.APP_ID)
    }

    private suspend fun getAppIdFromRemote(): String {
        val appIdFromMock = remote.getAppId()
        pref.addString(LocalPreferencesKeys.APP_ID, appIdFromMock)
        return appIdFromMock
    }

}