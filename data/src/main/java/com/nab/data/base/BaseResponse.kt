package com.nab.data.base

interface BaseResponse<out DomainObject : Any> {
    fun toDomain(): DomainObject
}