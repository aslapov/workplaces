package com.workplaces.aslapov.data.auth

import com.workplaces.aslapov.data.profile.AppCache
import com.workplaces.aslapov.domain.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenSource: TokenSharedPreferenceSource,
    private val cache: AppCache
) : AuthRepository {

    override var accessToken: String? = tokenSource.getAccessToken()
    override var refreshToken: String? = tokenSource.getRefreshToken()

    override fun isUserLoggedIn(): Boolean = accessToken != null

    override suspend fun register(email: String, password: String) {
        val userCredentials = UserCredentials(email, password)
        val token = authApi.register(userCredentials)
        saveToken(token)
    }

    override suspend fun login(email: String, password: String) {
        val userCredentials = UserCredentials(email, password)
        val token = authApi.login(userCredentials)
        saveToken(token)
    }

    override suspend fun logout(): Flow<Unit> {
        return flow {
            try {
                requireNotNull(accessToken)
                authApi.logout("Bearer $accessToken")
            } finally {
                tokenSource.logout()
                accessToken = null
                refreshToken = null
                cache.setUser(null)
                emit(Unit)
            }
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
