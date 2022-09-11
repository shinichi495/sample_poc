package com.nab.weatherforecast.ui.common

import com.nab.domain.models.ErrorEntity
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


fun errorTextCase (errorEntity: ErrorEntity) : String = when(errorEntity) {
    is ErrorEntity.Network -> "Network Error"
    is ErrorEntity.NotFound -> "Not found Error"
    is ErrorEntity.AccessDenied -> "Access Denied"
    is ErrorEntity.ServiceUnAvailable -> "Server is not availble"
    is ErrorEntity.DBError -> "Something wrong with Database"
    is ErrorEntity.Input -> "Your input is wrong , please input more 3 character"
    else -> "Unknown"
}


public fun isDeviceRooted(): Boolean {
    return checkRootMethod1() || checkRootMethod2() || checkRootMethod3()
}

private fun checkRootMethod1(): Boolean {
    val buildTags = android.os.Build.TAGS
    return buildTags != null && buildTags.contains("test-keys")
}

private fun checkRootMethod2(): Boolean {
    val paths = arrayOf("/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su")
    for (path in paths) {
        if (File(path).exists()) return true
    }
    return false
}

private fun checkRootMethod3(): Boolean {
    var process: Process? = null
    return try {
        process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
        val `in` = BufferedReader(InputStreamReader(process!!.inputStream))
        `in`.readLine() != null
    } catch (t: Throwable) {
        false
    } finally {
        process?.destroy()
    }
}