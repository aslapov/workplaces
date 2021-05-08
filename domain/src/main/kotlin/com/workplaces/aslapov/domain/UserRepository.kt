package com.workplaces.aslapov.domain

interface UserRepository {
    var user: User?
    fun isUserLoggedIn(): Boolean
    suspend fun register(email: String, password: String): ResponseResult
    suspend fun login(email: String, password: String): ResponseResult
    suspend fun updateUser(user: User): ResponseResult
    suspend fun logout(): ResponseResult
}

sealed class ResponseResult
object ResponseResultSuccess : ResponseResult()
data class ResponseResultError(val error: String) : ResponseResult()
