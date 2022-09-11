package com.nab.data.cache

import com.nab.domain.interfaces.GetWeatherHistoryDataSource
import com.nab.domain.models.GetWeatherResult
import io.realm.RealmConfiguration

class RealmOpenWeatherDataSource : GetWeatherHistoryDataSource {

    private val realmConfig = RealmConfiguration.Builder()
        .schemaVersion(realmVersion)
        .build()

    private val dbHelper = WeatherHistoryDbHelper(realmConfig)

    override suspend fun getInCache(q: String): GetWeatherResult {
        return dbHelper.retrieveFilterByQuery(q)
    }

    override suspend fun addInCache(weatherResult: GetWeatherResult) {
        dbHelper.insertList(weatherResult)
    }
}