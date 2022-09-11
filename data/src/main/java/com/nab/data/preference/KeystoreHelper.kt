package com.nab.data.preference

import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.*
import javax.crypto.spec.GCMParameterSpec


class KeystoreHelper private constructor() {

    private val ANDROID_KEYSTORE = "AndroidKeyStore"
    private val TRANSFORMATION = "AES/GCM/NoPadding"


    init {
        initKeyStore()
    }

    private object HOLDER {
        var INSTANCE = KeystoreHelper()
    }

    companion object {
        @JvmStatic
        fun getInstance(): KeystoreHelper {
            return HOLDER.INSTANCE
        }
    }

    lateinit var keyStore: KeyStore

    @Throws(
        KeyStoreException::class,
        CertificateException::class,
        NoSuchAlgorithmException::class,
        IOException::class
    )
    private fun initKeyStore() {
        keyStore =
            KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)
    }

    @Throws(
        UnrecoverableEntryException::class,
        NoSuchAlgorithmException::class,
        KeyStoreException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class,
        InvalidAlgorithmParameterException::class
    )
    fun decryptData(alias: String, encryptedData: ByteArray?, encryptionIv: ByteArray?): String? {
        val cipher =
            Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(128, encryptionIv)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(alias), spec)
        return String(cipher.doFinal(encryptedData), StandardCharsets.UTF_8)
    }

    @Throws(
        UnrecoverableEntryException::class,
        NoSuchAlgorithmException::class,
        KeyStoreException::class,
        NoSuchProviderException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        IOException::class,
        InvalidAlgorithmParameterException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class,
        CertificateException::class
    )
    fun encryptText(context: Context?, alias: String, textToEncrypt: String) {
        val cipher =
            Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(alias))
        val iv = cipher.iv
        val encryption = cipher.doFinal(textToEncrypt.toByteArray(StandardCharsets.UTF_8))
        KeyStoreSharedPreferenceHelper().setProperty(context!!, alias, encryption, iv)
    }

    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchProviderException::class,
        InvalidAlgorithmParameterException::class,
        KeyStoreException::class,
        UnrecoverableEntryException::class,
        CertificateException::class,
        IOException::class
    )
    private fun getSecretKey(alias: String): SecretKey {
        val keyStore =
            KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)
        val keyGenerator: KeyGenerator
        return if (!keyStore.containsAlias(alias)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES,
                    ANDROID_KEYSTORE
                )
                keyGenerator.init(
                    KeyGenParameterSpec.Builder(
                        alias,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                        .build()
                )
            } else {
                keyGenerator = KeyGenerator.getInstance(
                    "AES",
                    ANDROID_KEYSTORE
                )
                keyGenerator.init(256, SecureRandom.getInstance("AES"))
            }
            keyGenerator.generateKey()
        } else {
            (keyStore.getEntry(alias, null) as KeyStore.SecretKeyEntry).secretKey
        }
    }


}