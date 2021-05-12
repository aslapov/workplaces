package com.workplaces.aslapov.data.auth

import android.content.Context
import androidx.core.content.edit
import java.util.*
import javax.inject.Inject

class TokenSharedPreferenceSource @Inject constructor(context: Context) {

    companion object {
        private const val TOKEN_SHARED_PREFS_FILE: String = "TOKEN_SHARED_PREFS_FILE"
        private const val ACCESS_TOKEN_KEY: String = "ACCESS_TOKEN_KEY"
        private const val REFRESH_TOKEN_KEY: String = "REFRESH_TOKEN_KEY"
    }

    private val sharedPreferences = context.getSharedPreferences(TOKEN_SHARED_PREFS_FILE, Context.MODE_PRIVATE)

    fun getAccessToken(): String? = sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
    fun getRefreshToken(): UUID? {
        val refreshToken = sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
        return if (refreshToken != null) UUID.fromString(refreshToken) else null
    }

    fun setTokens(accessToken: String, refreshToken: UUID) {
        sharedPreferences.edit {
            putString(ACCESS_TOKEN_KEY, accessToken)
            putString(REFRESH_TOKEN_KEY, refreshToken.toString())

            apply()
        }
    }

    fun logout() {
        sharedPreferences.edit {
            putString(ACCESS_TOKEN_KEY, null)
            putString(REFRESH_TOKEN_KEY, null)

            apply()
        }
    }
}
