package com.nab.weatherforecast.mock

import com.nab.domain.usecases.GetMockApiKeyBaseUseCase
import com.nab.weatherforecast.base.BaseTestUseCase
import com.nab.weatherforecast.util.State
import kotlinx.coroutines.flow.Flow

class FakeGetMockApiKeyUseCase(state: State) : BaseTestUseCase<Unit, String>(state),
    GetMockApiKeyBaseUseCase {
    override fun getValue(params: Unit): String = "apiIdTest"

    override suspend fun run(params: Unit): Flow<String> = excute(params)
}