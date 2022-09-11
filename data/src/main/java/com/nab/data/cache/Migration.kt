package com.nab.data.cache

import io.realm.RealmMigration

val realmVersion = 2L

val migration = RealmMigration { realm, oldVersion, newVersion ->
    if (oldVersion == 1L) {
        val petSchema = realm.schema.get("WeatherRealm")

        petSchema?.let {
            petSchema?.addRealmListField("pets", it)
        }
    }
}