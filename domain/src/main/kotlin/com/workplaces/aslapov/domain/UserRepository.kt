package com.workplaces.aslapov.domain

import java.util.*

interface UserRepository {
    var user: User?
    var accessToken: String?
    var refreshToken: UUID?
    fun isUserLoggedIn(): Boolean
    suspend fun register(email: String, password: String)
    suspend fun login(email: String, password: String)
    suspend fun updateUser(user: User)
    suspend fun logout()
    suspend fun refreshToken(): String
    suspend fun getMyUser(): User
}
