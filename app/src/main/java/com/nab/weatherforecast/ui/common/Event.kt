package com.nab.weatherforecast.ui.common

class Event<out T>(private val content : T) {

    var needHandle = false
        private set

    fun getNeedHandle () = if (needHandle) null else content.also { needHandle = true }
}

