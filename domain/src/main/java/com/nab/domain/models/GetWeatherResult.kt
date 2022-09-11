package com.nab.domain.models

data class GetWeatherResult(
    var query : String? = null,
    var weathers: List<Weather>? = null
) {
    data class Weather(
        var date: String? = null,
        var avgtem: Double? = null,
        var pressuare: Int? = null,
        var humidity: Int? = null,
        var desc: String? = null
    )
}
