package com.nab.domain.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

interface UseCase<in Parameter, out Result> {
    suspend fun run(params: Parameter): Flow<Result>
    suspend operator fun invoke(params: Parameter): Flow<Result> {
        val result = run(params).flowOn(Dispatchers.IO)
        return result.flowOn(Dispatchers.Main)
    }
}