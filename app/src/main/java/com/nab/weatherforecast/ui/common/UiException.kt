package com.nab.weatherforecast.ui.common

class UiException(val type: UiExceptionType) : Exception() {

    override val message: String?
        get() = "UI Excetion"
}

enum class UiExceptionType {
    WRONG_INPUT_TYPE
}