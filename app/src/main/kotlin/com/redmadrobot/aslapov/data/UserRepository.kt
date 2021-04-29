package com.redmadrobot.aslapov.data

import com.redmadrobot.aslapov.data.local.UserSource
import com.redmadrobot.aslapov.data.remote.AuthApi
import com.redmadrobot.aslapov.data.remote.ProfileApi
import com.redmadrobot.aslapov.data.remote.UserCredentials
import com.redmadrobot.aslapov.profile.User
import java.net.ConnectException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val localSource: UserSource,
    private val remoteApiSource: AuthApi,
    private val remoteProfileSource: ProfileApi
) {
    var user: User? = null
        private set

    init {
        user = localSource.getUser()
    }

    fun isUserLoggedIn(): Boolean = user != null

    suspend fun register(email: String, password: String): ResponseResult {
        return try {
            val userCredentials = UserCredentials(email, password)
            remoteApiSource.register(userCredentials)
            saveUser()
            ResponseResultSuccess
        } catch (e: ConnectException) {
            ResponseResultError(e.message ?: "Ошибка регистрации")
        }
    }

    suspend fun login(username: String, password: String): ResponseResult {
        return try {
            val userCredentials = UserCredentials(username, password)
            remoteApiSource.login(userCredentials)
            saveUser()
            ResponseResultSuccess
        } catch (e: ConnectException) {
            ResponseResultError(e.message ?: "Ошибка авторизации")
        }
    }

    suspend fun logout(): ResponseResult {
        return try {
            remoteApiSource.logout()
            localSource.logout()
            user = null
            ResponseResultSuccess
        } catch (e: ConnectException) {
            ResponseResultError(e.message ?: "Ошибка выхода из аккаунта")
        }
    }

    private suspend fun saveUser() {
        val me: User = remoteProfileSource.getMe()
        localSource.setUser(me)
        user = me
    }
}

sealed class ResponseResult
object ResponseResultSuccess : ResponseResult()
data class ResponseResultError(val error: String) : ResponseResult()
