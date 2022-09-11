package com.nab.domain.models

sealed class ErrorEntity {
    object Network : ErrorEntity()
    object NotFound : ErrorEntity()
    object AccessDenied : ErrorEntity()
    object ServiceUnAvailable : ErrorEntity()
    object Unknown : ErrorEntity()
    object DBError : ErrorEntity()
    object Input : ErrorEntity()
}
