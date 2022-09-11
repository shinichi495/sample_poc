package com.nab.weatherforecast.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nab.weatherforecast.util.CoroutineTestRule
import com.nab.weatherforecast.util.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

abstract class BaseViewModelTest{

    @get: Rule
    open val instantExcutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get: Rule
    open val coroutineTestRule = CoroutineTestRule()

    abstract fun prepareViewModel(state: State)
}