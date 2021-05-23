package com.workplaces.aslapov.data.auth

import com.workplaces.aslapov.data.auth.localstore.TokenStore
import com.workplaces.aslapov.data.auth.network.AuthApi
import com.workplaces.aslapov.data.auth.network.model.Token
import com.workplaces.aslapov.data.auth.network.model.UserCredentialsNetwork
import com.workplaces.aslapov.domain.login.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenSource: TokenStore
) : AuthRepository {

    override val accessToken: String? get() = tokenSource.getAccessToken()
    override val refreshToken: String? get() = tokenSource.getRefreshToken()

    private val _logoutEvent = MutableStateFlow(false)
    override val logoutEvent: StateFlow<Boolean> = _logoutEvent

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
            /* TODO из-за "хака" в виде сброса состояния
                для успешного отлавливания события logout'а в нижней строке
                следует перейти на SharedFlow */
            _logoutEvent.value = false

            requireNotNull(accessToken)
            authApi.logout("Bearer $accessToken")
        } finally {
            tokenSource.logout()
            _logoutEvent.value = true
        }
    }

    override suspend fun refreshToken(): String {
        val token = authApi.refresh(requireNotNull(refreshToken))
        saveToken(token)
        return token.accessToken
    }

    private fun saveToken(token: Token) {
        tokenSource.setTokens(token.accessToken, token.refreshToken)
    }
}
