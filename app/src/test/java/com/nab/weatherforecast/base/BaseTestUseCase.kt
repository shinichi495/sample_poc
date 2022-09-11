package com.nab.weatherforecast.base

import com.nab.weatherforecast.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseTestUseCase<in I, out O> ( val state : State) {
    fun excute(param : I ) : Flow<O> = flow {
        val value = getValue(param)
        if (state == State.SUCCESS) emit(value)
        if (state == State.ERROR) throw Exception("Something wrong")

    }
    abstract fun getValue(params : I) : O
}