package com.workplaces.aslapov.data.auth

import com.workplaces.aslapov.data.auth.localstore.TokenStore
import com.workplaces.aslapov.data.auth.network.AuthApi
import com.workplaces.aslapov.data.auth.network.model.RefreshTokenRequest
import com.workplaces.aslapov.data.auth.network.model.Token
import com.workplaces.aslapov.data.auth.network.model.UserCredentialsNetwork
import com.workplaces.aslapov.data.util.extensions.checkIsSuccessful
import com.workplaces.aslapov.domain.login.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenSource: TokenStore
) : AuthRepository {

    override val accessToken: String? get() = tokenSource.getAccessToken()
    override val refreshToken: String? get() = tokenSource.getRefreshToken()

    private val _logoutFlow: MutableSharedFlow<Unit> = MutableSharedFlow()
    override val logoutFlow: SharedFlow<Unit> = _logoutFlow

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
                .checkIsSuccessful()
        } finally {
            tokenSource.logout()
            _logoutFlow.emit(Unit)
        }
    }

    override suspend fun refreshToken(): String {
        val refreshToken = requireNotNull(refreshToken)
        return authApi.refresh(RefreshTokenRequest(refreshToken))
            .also { saveToken(it) }
            .accessToken
    }

    private fun saveToken(token: Token) {
        tokenSource.setTokens(token.accessToken, token.refreshToken)
    }
}
