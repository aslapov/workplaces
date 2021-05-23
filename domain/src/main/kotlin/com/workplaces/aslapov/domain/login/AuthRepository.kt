package com.workplaces.aslapov.domain.login

import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {

    val accessToken: String?
    val refreshToken: String?

    val logoutEvent: StateFlow<Boolean>

    fun isUserLoggedIn(): Boolean

    suspend fun register(email: String, password: String)
    suspend fun login(email: String, password: String)
    suspend fun logout()
    suspend fun refreshToken(): String
}
