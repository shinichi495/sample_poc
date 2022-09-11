package com.nab.weatherforecast.util

import com.nab.common.Logger
import com.nab.domain.interfaces.ErrorHandler
import com.nab.domain.models.ErrorEntity

object NoOpErrorEntity : ErrorHandler {
    override fun getError(throwable: Throwable): ErrorEntity {
        TODO("Not yet implemented")
    }
}
object NoOpLogger : Logger {
    override fun trace(message: String) {}

    override fun trace(message: String, throwable: Throwable) {}

    override fun debug(message: String) {}

    override fun debug(message: String, throwable: Throwable) {}

    override fun info(message: String) {}

    override fun info(message: String, throwable: Throwable) {}

    override fun warn(message: String) {}

    override fun warn(message: String, throwable: Throwable) {}

    override fun error(message: String) {}

    override fun error(message: String, throwable: Throwable) {}

    override fun fatal(message: String) {}

    override fun fatal(message: String, throwable: Throwable) {}
}

object NoOpErrorHandler : ErrorHandler {
    override fun getError(throwable: Throwable): ErrorEntity {
        TODO("Not yet implemented")
    }

}