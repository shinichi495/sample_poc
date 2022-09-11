package com.nab.weatherforecast

import android.app.Application
import com.nab.weatherforecast.ui.common.isDeviceRooted
import io.realm.Realm
import io.realm.internal.Util

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        if (isDeviceRooted()) {
            android.os.Process.killProcess(android.os.Process.myPid())
        } else {
            Realm.init(this)
        }
    }



}