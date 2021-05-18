package com.workplaces.aslapov.domain.login

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalCoroutinesApi
interface AuthRepository {

    val accessToken: String?
    val refreshToken: String?

    val logoutEvent: MutableStateFlow<Boolean>

    fun isUserLoggedIn(): Boolean

    suspend fun register(email: String, password: String)
    suspend fun login(email: String, password: String)
    suspend fun logout()
    suspend fun refreshToken(): String
}
