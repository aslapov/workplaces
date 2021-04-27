package com.redmadrobot.aslapov.data

import com.redmadrobot.aslapov.data.local.UserSource
import com.redmadrobot.aslapov.data.remote.AuthApi
import com.redmadrobot.aslapov.data.remote.ProfileApi
import com.redmadrobot.aslapov.data.remote.UserCredentials
import com.redmadrobot.aslapov.profile.User
import java.lang.Exception
import javax.inject.Inject

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

    suspend fun register(username: String, password: String): AuthResult {
        return try {
            val userCredentials = UserCredentials(username, password)
            remoteApiSource.register(userCredentials)
            saveUser()
            AuthResult.AUTHORIZED
        } catch (e: Exception) {
            AuthResult.FAIL
        }
    }

    suspend fun login(username: String, password: String): AuthResult {
        return try {
            val userCredentials = UserCredentials(username, password)
            remoteApiSource.login(userCredentials)
            saveUser()
            AuthResult.AUTHORIZED
        } catch (e: Exception) {
            AuthResult.FAIL
        }
    }

    suspend fun logout(): LogoutResult {
        return try {
            remoteApiSource.logout()
            localSource.logout()
            user = null
            LogoutResult.LOGGEDOUT
        } catch (e: Exception) {
            LogoutResult.UNAUTHORIZED
        }
    }

    enum class AuthResult {
        AUTHORIZED,
        FAIL
    }

    enum class LogoutResult {
        LOGGEDOUT,
        UNAUTHORIZED
    }

    private suspend fun saveUser() {
        val me: User = remoteProfileSource.getMe()
        localSource.setUser(me)
        user = me
    }
}
