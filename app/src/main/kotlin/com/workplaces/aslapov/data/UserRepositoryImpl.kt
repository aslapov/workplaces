package com.workplaces.aslapov.data

import com.workplaces.aslapov.domain.*
import java.net.ConnectException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userSource: UserSource,
    private val authApi: FakeAuthApi,
    private val profileApi: FakeProfileApi
) : UserRepository {
    override var user: User? = null

    init {
        user = userSource.getUser()
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

    override suspend fun login(username: String, password: String): ResponseResult {
        return try {
            authApi.login()
            saveUser()
            ResponseResultSuccess
        } catch (e: ConnectException) {
            ResponseResultError(e.message ?: "Ошибка авторизации")
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

    private suspend fun saveUser() {
        val me: User = profileApi.getMe()
        userSource.setUser(me)
        user = me
    }
}
