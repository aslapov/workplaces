package com.workplaces.aslapov.data.auth.localstore

import android.content.Context
import androidx.core.content.edit
import javax.inject.Inject

class TokenSharedPreferenceSource @Inject constructor(context: Context) : TokenStore {

    companion object {
        private const val TOKEN_SHARED_PREFS_FILE: String = "TOKEN_SHARED_PREFS_FILE"
        private const val ACCESS_TOKEN_KEY: String = "ACCESS_TOKEN_KEY"
        private const val REFRESH_TOKEN_KEY: String = "REFRESH_TOKEN_KEY"
    }

    private val sharedPreferences = context.getSharedPreferences(TOKEN_SHARED_PREFS_FILE, Context.MODE_PRIVATE)

    override fun getAccessToken(): String? = sharedPreferences.getString(ACCESS_TOKEN_KEY, null)

    override fun getRefreshToken(): String? = sharedPreferences.getString(REFRESH_TOKEN_KEY, null)

    override fun setTokens(accessToken: String, refreshToken: String) {
        sharedPreferences.edit {
            putString(ACCESS_TOKEN_KEY, accessToken)
            putString(REFRESH_TOKEN_KEY, refreshToken.toString())

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
