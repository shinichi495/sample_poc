package com.nab.weatherforecast.ui.weather


import androidx.lifecycle.MutableLiveData
import com.nab.common.Logger
import com.nab.domain.interfaces.ErrorHandler
import com.nab.domain.models.ErrorEntity
import com.nab.domain.models.GetWeatherResult
import com.nab.domain.usecases.GetMockApiKeyBaseUseCase
import com.nab.domain.usecases.WeatherBaseUseCase
import com.nab.weatherforecast.ui.common.BaseViewModel
import com.nab.weatherforecast.ui.common.Event
import com.nab.weatherforecast.ui.common.UiExceptionType
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job

class WeatherViewModel(
    private val weatherUsecase: WeatherBaseUseCase,
    private val getMockUsercase: GetMockApiKeyBaseUseCase,
    private val logger: Logger,
    private val errorEntity: ErrorHandler
) : BaseViewModel() {

    val q = MutableLiveData<String>()
    val shouldShowNoResultsText = MutableLiveData<Boolean>()
    val shouldShowLoadingIndicator = MutableLiveData<Boolean>()
    val showErrorToastEvent = MutableLiveData<Event<ErrorEntity>>()
    val searchResultItems = MutableLiveData<List<GetWeatherResult.Weather>?>()
    val appId = MutableLiveData<String>()


    private var getAppApiKeyJob: Job? = null
    private var getWeathersJob: Job? = null

    override fun onCleared() {
        super.onCleared()
        getWeathersJob?.cancel()
        getAppApiKeyJob?.cancel()
    }

    fun onClickGetWeather() {
        getWeathersJob?.cancel()
        getWeathersJob = launchCoroutine {
            shouldShowLoadingIndicator.value = true
            q.value?.let {
                weatherUsecase(it).collect { result ->
                    logger.info("weather results : $result")
                    searchResultItems.value = result?.weathers
                }
            }
            shouldShowLoadingIndicator.value = false
        }
    }


    fun toHandleEcryptData() {
        getAppApiKeyJob?.cancel()
        getAppApiKeyJob = launchCoroutine {
            getMockUsercase(Unit).collect { id ->
                shouldShowLoadingIndicator.value = false
                appId.value = id
            }
        }
    }

    override val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            logger.warn("An error happen when get weathers")
            shouldShowLoadingIndicator.value = false
            showErrorToastEvent.value = Event(errorEntity.getError(exception))
        }

}



