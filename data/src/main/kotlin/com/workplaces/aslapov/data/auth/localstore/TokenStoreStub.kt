package com.workplaces.aslapov.data.auth.localstore

class TokenStoreStub : TokenStore {

    private var _accessToken: String? = null
    private var _refreshToken: String? = null

    override fun getAccessToken(): String? = _accessToken

    override fun getRefreshToken(): String? = _refreshToken

    override fun setTokens(accessToken: String, refreshToken: String) {
        _accessToken = accessToken
        _refreshToken = refreshToken
    }

    override fun logout() {
        _accessToken = null
        _refreshToken = null
    }
}
