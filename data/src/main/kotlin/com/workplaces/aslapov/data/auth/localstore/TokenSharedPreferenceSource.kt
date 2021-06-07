package com.workplaces.aslapov.data.auth.localstore

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class TokenSharedPreferenceSource @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : TokenStore {

    companion object {
        private const val ACCESS_TOKEN_KEY: String = "ACCESS_TOKEN"
        private const val REFRESH_TOKEN_KEY: String = "REFRESH_TOKEN"
    }

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
