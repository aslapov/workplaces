package com.workplaces.aslapov.data.auth.localstore

interface TokenStore {
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun setTokens(accessToken: String, refreshToken: String)
    fun logout()
}
