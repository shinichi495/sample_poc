package com.nab.data.openweather

import com.nab.domain.interfaces.ErrorHandler
import com.nab.domain.models.ErrorEntity
import io.realm.exceptions.RealmException
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException
import java.net.HttpURLConnection
import java.net.UnknownHostException

class GeneralErrorHandlerImpl : ErrorHandler {
    override fun getError(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is UnknownHostException -> ErrorEntity.Network
            is IOException -> ErrorEntity.Input
            is HttpException -> {
                when (throwable.code()) {
                    HttpURLConnection.HTTP_NOT_FOUND ->
                        ErrorEntity.NotFound
                    HttpURLConnection.HTTP_FORBIDDEN, HttpURLConnection.HTTP_UNAUTHORIZED ->
                        ErrorEntity.AccessDenied
                    HttpURLConnection.HTTP_UNAVAILABLE ->
                        ErrorEntity.ServiceUnAvailable
                    else ->
                        ErrorEntity.Unknown
                }
            }
            is InvalidObjectException -> ErrorEntity.Input
            is RealmException -> ErrorEntity.DBError
            else -> ErrorEntity.Unknown
        }
    }
}