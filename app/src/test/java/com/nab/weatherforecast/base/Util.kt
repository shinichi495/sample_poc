package com.nab.weatherforecast.base

import androidx.lifecycle.LiveData
import com.nab.weatherforecast.util.OneTimeObserver

fun <T> LiveData<T>.observeOnce(onChangeHandler : (T) -> Unit) {
    val observer = OneTimeObserver(onChangeHandler)
    observe(observer, observer)
}