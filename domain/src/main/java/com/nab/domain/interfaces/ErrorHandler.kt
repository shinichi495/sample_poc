package com.nab.domain.interfaces

import com.nab.domain.models.ErrorEntity

interface ErrorHandler {
    fun getError (throwable: Throwable) : ErrorEntity
}