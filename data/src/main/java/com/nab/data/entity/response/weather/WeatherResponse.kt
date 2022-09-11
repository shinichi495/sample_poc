package com.nab.data.entity.response.weather

import com.google.gson.annotations.SerializedName
import com.nab.data.base.BaseResponse
import com.nab.domain.models.GetWeatherResult
import java.text.SimpleDateFormat
import java.util.*

data class WeatherResponse(
    @SerializedName("city") var city: City? = City(),
    @SerializedName("cod") var cod: String? = null,
    @SerializedName("message") var message: Double? = null,
    @SerializedName("cnt") var cnt: Int? = null,
    @SerializedName("list") var list: ArrayList<List>? = arrayListOf()
) : BaseResponse<GetWeatherResult> {

    data class Coord(
        @SerializedName("lon") var lon: Double? = null,
        @SerializedName("lat") var lat: Double? = null
    )

    data class City(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("name") var name: String? = null,
        @SerializedName("coord") var coord: Coord? = Coord(),
        @SerializedName("country") var country: String? = null,
        @SerializedName("population") var population: Int? = null,
        @SerializedName("timezone") var timezone: Int? = null
    )

    data class Temp(
        @SerializedName("day") var day: Double? = null,
        @SerializedName("min") var min: Double? = null,
        @SerializedName("max") var max: Double? = null,
        @SerializedName("night") var night: Double? = null,
        @SerializedName("eve") var eve: Double? = null,
        @SerializedName("morn") var morn: Double? = null
    )

    data class FeelsLike(
        @SerializedName("day") var day: Double? = null,
        @SerializedName("night") var night: Double? = null,
        @SerializedName("eve") var eve: Double? = null,
        @SerializedName("morn") var morn: Double? = null
    )

    data class Weather(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("main") var main: String? = null,
        @SerializedName("description") var description: String? = null,
        @SerializedName("icon") var icon: String? = null
    )

    data class List(
        @SerializedName("dt") var dt: Int? = null,
        @SerializedName("sunrise") var sunrise: Int? = null,
        @SerializedName("sunset") var sunset: Int? = null,
        @SerializedName("temp") var temp: Temp? = Temp(),
        @SerializedName("feels_like") var feelsLike: FeelsLike? = FeelsLike(),
        @SerializedName("pressure") var pressure: Int? = null,
        @SerializedName("humidity") var humidity: Int? = null,
        @SerializedName("weather") var weather: ArrayList<Weather> = arrayListOf(),
        @SerializedName("speed") var speed: Double? = null,
        @SerializedName("deg") var deg: Int? = null,
        @SerializedName("gust") var gust: Double? = null,
        @SerializedName("clouds") var clouds: Int? = null,
        @SerializedName("pop") var pop: Double? = null,
        @SerializedName("rain") var rain: Double? = null
    )

    override fun toDomain() = GetWeatherResult().also {
        it.weathers = list?.map {
            GetWeatherResult.Weather(
                parseToDataFormat(it.dt.toString()),
                parseKevinToCelisios(it.temp?.max, it.temp?.min),
                it.pressure,
                it.humidity,
                it.weather[0].description
            )
        }
    }

    private fun parseToDataFormat(tms: String): String? {
        val sdf = SimpleDateFormat("EEE, dd/MM/yyyy", Locale.US)
        return sdf.format(Date(tms.toLong() * 1000))
    }

    private fun parseKevinToCelisios(min: Double?, max: Double?): Double {
        if (min == null || max == null) return 0.0
        val sum = listOf(min, max)
        val avg = sum.average() - 272.15
        val convertToString = String.format("%.2f", avg)
        val result = java.lang.Double.parseDouble(convertToString)
        return result
    }
}