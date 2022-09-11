package com.nab.data.preference



interface ISecureLocalStorage {
    fun contains(entityId: LocalPreferencesKeys) : Boolean
    fun getString (entityId : LocalPreferencesKeys) : String
    fun addString (entityId: LocalPreferencesKeys, value : String)
}