package com.nab.weatherforecast.ui.weather

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.nab.weatherforecast.base.BaseViewModelTest
import com.nab.weatherforecast.base.observeOnce
import com.nab.weatherforecast.mock.FakeGetMockApiKeyUseCase
import com.nab.weatherforecast.mock.FakeWeatherUseCase
import com.nab.weatherforecast.util.NoOpErrorHandler
import com.nab.weatherforecast.util.NoOpLogger
import com.nab.weatherforecast.util.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1],manifest=Config.NONE)
@ExperimentalCoroutinesApi
class WeatherViewModelTest : BaseViewModelTest() {

    private lateinit var weatherViewModel: WeatherViewModel

    @After
    fun tearDown() {
        coroutineTestRule.dispatcher.cancel()
    }

    @Test
    fun `should get apiKey success`() {
        runTest {
            prepareViewModel(State.SUCCESS)
            weatherViewModel.toHandleEcryptData()
            advanceUntilIdle()
            weatherViewModel.appId.observeOnce { appId ->
                Truth.assertThat(appId).isNotEmpty()
            }
            weatherViewModel.shouldShowLoadingIndicator.observeOnce { should ->
                Truth.assertThat(should).isFalse()
            }
        }

    }

    @Test
    fun `should get apiKey fail`() {
        runTest {
            prepareViewModel(State.ERROR)
            weatherViewModel.toHandleEcryptData()
            advanceUntilIdle()
            weatherViewModel.appId.observeOnce { appId ->
                Truth.assertThat(appId).isEmpty()
            }
            weatherViewModel.showErrorToastEvent.observeOnce {
                Truth.assertThat(it).isNotNull()
            }
            weatherViewModel.shouldShowLoadingIndicator.observeOnce { should ->
                Truth.assertThat(should).isFalse()
            }

        }
    }

    @Test
    fun `should get weathers success`() = runTest {

        prepareViewModel(State.SUCCESS)
        weatherViewModel.onClickGetWeather()
        weatherViewModel.q.value = "saigon"
        advanceUntilIdle()
        weatherViewModel.searchResultItems.observeOnce { weathers ->
            Truth.assertThat(weathers).isNotEmpty()
        }

    }

    @Test
    fun `should get weather error`() = runTest {
        prepareViewModel(State.ERROR)
        weatherViewModel.q.value = "error"
        weatherViewModel.onClickGetWeather()
        advanceUntilIdle()
        weatherViewModel.shouldShowLoadingIndicator.observeOnce { should ->
            Truth.assertThat(should).isFalse()
        }
    }

    override fun prepareViewModel(state: State) {
        val weatherUseCase = FakeWeatherUseCase(state = state)
        val getMockApiUse = FakeGetMockApiKeyUseCase(state = state)
        weatherViewModel = WeatherViewModel(
            weatherUseCase, getMockApiUse,
            NoOpLogger, NoOpErrorHandler
        )
    }
}