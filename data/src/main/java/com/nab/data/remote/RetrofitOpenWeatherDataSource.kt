package com.nab.data.remote

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.nab.domain.interfaces.GetWeatherDataSource
import com.nab.domain.models.GetWeatherResult
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitOpenWeatherDataSource : GetWeatherDataSource {
    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/"
    }

    private val interceptor = HttpLoggingInterceptor()

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)).build()

    private val openWeatherService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttp)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create()
            )
        )
        .build()
        .create(OpenWeatherService::class.java)

    override suspend fun getWeather(q: String, appId : String): GetWeatherResult {
        val response = openWeatherService.getWeather(q, 7, appId)
        return response.toDomain()
        }

    // mock api to get appId
    override suspend fun getAppId(): String = "60c6fbeb4b93ac653c492ba806fc346d"

}


