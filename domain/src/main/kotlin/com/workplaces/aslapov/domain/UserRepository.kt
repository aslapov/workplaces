package com.workplaces.aslapov.domain

import java.util.*

interface UserRepository {
    var user: User?
    var accessToken: String?
    var refreshToken: UUID?
    fun isUserLoggedIn(): Boolean
    suspend fun register(email: String, password: String): ResponseResult
    suspend fun login(email: String, password: String): ResponseResult
    suspend fun updateUser(user: User): ResponseResult
    suspend fun logout(): ResponseResult
    suspend fun refreshToken(): String
}

sealed class ResponseResult
object ResponseResultSuccess : ResponseResult()
data class ResponseResultError(val error: String) : ResponseResult()
