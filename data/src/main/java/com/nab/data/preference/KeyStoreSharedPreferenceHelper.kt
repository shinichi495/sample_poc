package com.nab.data.preference

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64

class KeyStoreSharedPreferenceHelper {
    private var prefs: SharedPreferences? = null
    fun contains(context: Context, key: String?): Boolean {
        initPrefs(context)
        return prefs!!.contains(key)
    }

    fun getProperty(context: Context, alias: String): EncryptedInfo {
        initPrefs(context)
        val info = EncryptedInfo()
        val encryptedText = prefs!!.getString(alias, null)
        val iv = prefs!!.getString(alias + "_iv", null)
        if (iv != null) {
            info.IV = Base64.decode(iv, Base64.DEFAULT)
        }
        if (encryptedText != null) {
            info.encryptedText = Base64.decode(encryptedText, Base64.DEFAULT)
        }
        return info
    }

    fun setProperty(context: Context, alias: String, encryptedValue: ByteArray?, iv: ByteArray?) {
        initPrefs(context)
        //Save the encrypted value to the pref manager
        prefs!!.edit().putString(alias, Base64.encodeToString(encryptedValue, Base64.DEFAULT))
            .apply()
        prefs!!.edit().putString(alias + "_iv", Base64.encodeToString(iv, Base64.DEFAULT)).apply()
    }

    private fun initPrefs(context: Context) {
        if (prefs == null) {
            prefs = context.getSharedPreferences("NAB_REFERENCE", Context.MODE_PRIVATE)
        }
    }
}

class EncryptedInfo {
    var encryptedText: ByteArray? = null
    var IV: ByteArray? = null
}