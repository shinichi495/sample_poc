package com.nab.data.base

interface IDbDataHelper<DomainObject, out DataObject> {
    fun toDomain(): DomainObject
    fun toData(domain: DomainObject?): DataObject
}