package com.workplaces.aslapov.data.auth.localstore

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.inject.Inject

class TokenSharedPreferenceSource @Inject constructor(context: Context) : TokenStore {

    companion object {
        private const val TOKEN_SHARED_PREFS_FILE: String = "TOKEN_SHARED_PREFS_FILE"
        private const val ACCESS_TOKEN_KEY: String = "ACCESS_TOKEN_KEY"
        private const val REFRESH_TOKEN_KEY: String = "REFRESH_TOKEN_KEY"
    }

    private val mainKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        TOKEN_SHARED_PREFS_FILE,
        mainKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
    )

    override fun getAccessToken(): String? = sharedPreferences.getString(ACCESS_TOKEN_KEY, null)

    override fun getRefreshToken(): String? = sharedPreferences.getString(REFRESH_TOKEN_KEY, null)

    override fun setTokens(accessToken: String, refreshToken: String) {
        sharedPreferences.edit {
            putString(ACCESS_TOKEN_KEY, accessToken)
            putString(REFRESH_TOKEN_KEY, refreshToken)

            apply()
        }
    }

    override fun logout() {
        sharedPreferences.edit {
            putString(ACCESS_TOKEN_KEY, null)
            putString(REFRESH_TOKEN_KEY, null)

            apply()
        }
    }
}
