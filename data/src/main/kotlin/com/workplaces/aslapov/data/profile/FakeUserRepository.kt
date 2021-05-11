package com.workplaces.aslapov.data.profile

import com.workplaces.aslapov.data.auth.FakeAuthApi
import com.workplaces.aslapov.domain.*
import java.net.ConnectException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeUserRepository @Inject constructor(
    private val userSource: UserSharedPreferencesSource,
    private val authApi: FakeAuthApi,
    private val profileApi: FakeProfileApi
) : UserRepository {
    override var user: User? = null

    override var accessToken: String? = null

    override var refreshToken: UUID? = null

    init {
        user = userSource.getUser()?.toUser()
    }

    override fun isUserLoggedIn(): Boolean = user != null

    override suspend fun register(email: String, password: String): ResponseResult {
        return try {
            authApi.register()
            saveUser()
            ResponseResultSuccess
        } catch (e: ConnectException) {
            ResponseResultError(e.message ?: "Ошибка регистрации")
        }
    }

    override suspend fun login(email: String, password: String): ResponseResult {
        return try {
            authApi.login()
            saveUser()
            ResponseResultSuccess
        } catch (e: ConnectException) {
            ResponseResultError(e.message ?: "Ошибка авторизации")
        }
    }

    override suspend fun updateUser(user: User): ResponseResult {
        return try {
            val updatedUser = profileApi.updateUser(user)
            saveUser(updatedUser)
            ResponseResultSuccess
        } catch (e: ConnectException) {
            ResponseResultError(e.message ?: "Ошибка обновления профиля")
        }
    }

    override suspend fun logout(): ResponseResult {
        return try {
            authApi.logout()
            userSource.logout()
            user = null
            ResponseResultSuccess
        } catch (e: ConnectException) {
            ResponseResultError(e.message ?: "Ошибка выхода из аккаунта")
        }
    }

    override suspend fun refreshToken(): String {
        TODO("Not yet implemented")
    }

    private suspend fun saveUser() {
        val me: UserNetwork = profileApi.getMe()
        saveUser(me)
    }
    private fun saveUser(userNetwork: UserNetwork) {
        userSource.setUser(userNetwork)
        user = userNetwork.toUser()
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
