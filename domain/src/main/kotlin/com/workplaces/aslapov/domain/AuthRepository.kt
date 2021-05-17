package com.workplaces.aslapov.domain

import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val accessToken: String?
    val refreshToken: String?

    fun isUserLoggedIn(): Boolean

    suspend fun register(email: String, password: String)
    suspend fun login(email: String, password: String)
    fun logout(): Flow<Unit>
    suspend fun refreshToken(): String
}
