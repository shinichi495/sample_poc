package com.nab.domain.usecases

import com.nab.domain.base.UseCase
import com.nab.domain.interfaces.IWeatherRepository
import kotlinx.coroutines.flow.Flow


typealias GetMockApiKeyBaseUseCase = UseCase<Unit, String>

class GetMockApiKeyUseCase(
    private val repository: IWeatherRepository
) : GetMockApiKeyBaseUseCase {
    override suspend fun run(params: Unit): Flow<String> = repository.fetchAppId()


}