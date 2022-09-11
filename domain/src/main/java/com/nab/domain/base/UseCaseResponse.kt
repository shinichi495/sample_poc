package com.nab.domain.base

import com.nab.domain.models.ErrorEntity

interface UseCaseResponse<in Response> {
    fun onSuccess (result : Response?)
    fun onFail (error : ErrorEntity)
}