package com.redmadrobot.aslapov.data.remote

interface AuthApi {
    suspend fun register(user: UserCredentials): SignInResponseBody
    suspend fun login(user: UserCredentials): SignInResponseBody
    suspend fun logout()
}
