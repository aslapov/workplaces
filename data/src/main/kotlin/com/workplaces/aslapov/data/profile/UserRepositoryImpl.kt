package com.workplaces.aslapov.data.profile

import com.workplaces.aslapov.data.auth.AuthApi
import com.workplaces.aslapov.data.auth.Token
import com.workplaces.aslapov.data.auth.TokenSharedPreferenceSource
import com.workplaces.aslapov.data.auth.UserCredentials
import com.workplaces.aslapov.domain.User
import com.workplaces.aslapov.domain.UserRepository
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val profileApi: ProfileApi,
    private val tokenSource: TokenSharedPreferenceSource
) : UserRepository {

    override var user: User? = null

    override var accessToken: String? = null

    override var refreshToken: UUID? = null

    override fun isUserLoggedIn(): Boolean = accessToken != null
    init {
        accessToken = tokenSource.getAccessToken()
        refreshToken = tokenSource.getRefreshToken()
    }

    override suspend fun register(email: String, password: String) {
        val userCredentials = UserCredentials(email, password)
        val token = authApi.register(userCredentials)
        saveToken(token)
    }

    override suspend fun login(email: String, password: String) {
        val userCredentials = UserCredentials(email, password)
        val token = authApi.login(userCredentials)
        saveToken(token)
        getMyUser()
    }

    override suspend fun updateUser(user: User) {
        val updatedUser = profileApi.updateMe(
            firstName = user.firstName,
            lastName = user.lastName,
            nickName = user.nickName,
            avatarUrl = user.avatarUrl,
            birthday = user.birthday
        )
        saveUser(updatedUser.toUser())
    }

    override suspend fun logout() {
        authApi.logout()
        tokenSource.logout()
        user = null
        accessToken = null
        refreshToken = null
    }

    override suspend fun refreshToken(): String {
        val token = authApi.refresh(requireNotNull(refreshToken))
        saveToken(token)
        return token.accessToken
    }

    override suspend fun getMyUser(): User {
        val me: User = profileApi.getMe().toUser()
        saveUser(me)
        return me
    }

    private fun saveToken(token: Token) {
        accessToken = token.accessToken
        refreshToken = token.refreshToken
        tokenSource.setTokens(token.accessToken, token.refreshToken)
    }
    private fun saveUser(user: User) {
        this.user = user
    }
}

private fun UserNetwork.toUser(): User {
    return User(
        firstName = this.firstName,
        lastName = this.lastName,
        nickName = this.nickName,
        avatarUrl = this.avatarUrl,
        birthday = this.birthday
    )
}
