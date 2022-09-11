package com.nab.weatherforecast.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class CoroutineTestRule(
    val dispatcher: TestDispatcher = StandardTestDispatcher()
) :
    TestWatcher() {

    override fun starting(description: Description) {
        super.starting(description)

        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}