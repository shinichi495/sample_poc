package com.nab.data.cache

import com.nab.data.base.IDbDataHelper
import com.nab.domain.models.GetWeatherResult
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import org.bson.types.ObjectId

open class WeatherHistory(
    @PrimaryKey
    var id: String = ObjectId().toHexString(),

    @Required
    var query: String? = "",

    @Required
    var date: String? = "",

    @Required
    var avgtem: Double? = 0.0,

    @Required
    var pressuare: Int? = 0,

    @Required
    var humidity: Int? = 0,

    @Required
    var desc: String? = ""

) : RealmObject(),
    IDbDataHelper<GetWeatherResult.Weather, WeatherHistory> {

    override fun toDomain(): GetWeatherResult.Weather = GetWeatherResult.Weather().also { domain ->
        domain.date = this.date
        domain.avgtem = this.avgtem
        domain.desc = this.desc
        domain.pressuare = this.pressuare
        domain.humidity = this.humidity
    }

    override fun toData(domain: GetWeatherResult.Weather?): WeatherHistory {
        this.date = domain?.date
        this.avgtem = domain?.avgtem
        this.desc = domain?.desc
        this.pressuare = domain?.pressuare
        this.humidity = domain?.humidity
        return this
    }

}