package com.workplaces.aslapov.data.auth

import com.workplaces.aslapov.data.AppCache
import com.workplaces.aslapov.data.auth.localstore.TokenSharedPreferenceSource
import com.workplaces.aslapov.data.auth.network.AuthApi
import com.workplaces.aslapov.data.auth.network.model.Token
import com.workplaces.aslapov.data.auth.network.model.UserCredentialsNetwork
import com.workplaces.aslapov.domain.login.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenSource: TokenSharedPreferenceSource,
    private val cache: AppCache
) : AuthRepository {

    override var accessToken: String? = tokenSource.getAccessToken()
    override var refreshToken: String? = tokenSource.getRefreshToken()

    override val logoutEvent = MutableStateFlow(false)

    override fun isUserLoggedIn(): Boolean = accessToken != null

    override suspend fun register(email: String, password: String) {
        val userCredentials = UserCredentialsNetwork(email, password)
        val token = authApi.register(userCredentials)
        saveToken(token)
    }

    override suspend fun login(email: String, password: String) {
        val userCredentials = UserCredentialsNetwork(email, password)
        val token = authApi.login(userCredentials)
        saveToken(token)
    }

    override suspend fun logout() {
        try {
            requireNotNull(accessToken)
            authApi.logout("Bearer $accessToken")
        } finally {
            tokenSource.logout()
            accessToken = null
            refreshToken = null
            cache.setUser(null)
            logoutEvent.value = true
        }
    }

    override suspend fun refreshToken(): String {
        val token = authApi.refresh(requireNotNull(refreshToken))
        saveToken(token)
        return token.accessToken
    }

    private fun saveToken(token: Token) {
        tokenSource.setTokens(token.accessToken, token.refreshToken)
        accessToken = token.accessToken
        refreshToken = token.refreshToken
    }
}
