package com.nab.data.cache

import com.nab.domain.models.GetWeatherResult
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import io.realm.kotlin.executeTransactionAwait

class WeatherHistoryDbHelper(private val config: RealmConfiguration) {

    suspend fun insertList(weatherResult: GetWeatherResult) {
        val realm = Realm.getInstance(config)
        val weatherDb = toDataList(weatherResult)
        realm.executeTransactionAwait { realmTransaction ->
            realmTransaction.insert(weatherDb)
        }
    }

    fun retrieveFilterByQuery(q: String): GetWeatherResult {
        val realm = Realm.getInstance(config)
        val filterWeathers = mutableListOf<GetWeatherResult.Weather>()
        realm.executeTransactionAsync { realmTransaction ->
            filterWeathers.addAll(
                realmTransaction.where(WeatherHistory::class.java)
                    .equalTo("query", q)
                    .findAll()
                    .map { weatherHistory ->
                        weatherHistory.toDomain()
                    })
        }
        return GetWeatherResult(q, filterWeathers)
    }


    private fun toDataList(domain: GetWeatherResult): RealmList<WeatherHistory>? {
        var saveList: RealmList<WeatherHistory>? = null
        domain.weathers?.let {
            saveList = RealmList<WeatherHistory>()
            saveList!!.addAll(
                it.map { weather ->
                    var wHistory = WeatherHistory()
                    wHistory.query = domain.query
                    wHistory.toData(weather)
                }
            )
        }
        return saveList
    }
}