package com.nab.data.preference

import android.content.Context
import android.content.SharedPreferences
import com.nab.common.Logger
import java.io.IOException

class KeystoreEncryptedLocalStorage(context: Context, logger: Logger) : ISecureLocalStorage {

    private var prefs: SharedPreferences? = null

    private var mContext: Context? = null

    private var keystoreHelper: KeystoreHelper? = null

    private val keyStoreSharedPreferenceHelper = KeyStoreSharedPreferenceHelper()


    init {
        prefs = context.getSharedPreferences("NAB_REFERENCE", Context.MODE_PRIVATE)
        mContext = context
        try {
            keystoreHelper = KeystoreHelper.getInstance()
        } catch (e: Exception) {
            logger.debug("KeystoreEncryptedLocalStorage", e)
        }
    }

    override fun contains(entityId: LocalPreferencesKeys): Boolean {
        mContext?.let {
            return keyStoreSharedPreferenceHelper.contains(it, entityId.name)
        }
        return false
    }

    override fun getString(entityId: LocalPreferencesKeys): String {
        var res = ""
        mContext?.let {
            val infor = keyStoreSharedPreferenceHelper.getProperty(it, entityId.name)
            ifLet(infor.encryptedText, infor.IV) { (encText, iv) ->
                res = keystoreHelper?.decryptData(entityId.name, encText, iv) ?: ""
            }
        }
        return res
    }

    override fun addString(entityId: LocalPreferencesKeys, value: String) {
        if (value == null) {
            return
        }
        try {
            keystoreHelper?.encryptText(mContext, entityId.name, value)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

