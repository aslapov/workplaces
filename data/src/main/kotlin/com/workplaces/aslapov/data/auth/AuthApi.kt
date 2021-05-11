package com.workplaces.aslapov.data.auth

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.*

interface AuthApi {
    @Headers("Content-Type: application/json")
    @POST("/auth/registration")
    suspend fun register(@Body userCredentials: UserCredentials): Token

    @POST("/auth/login")
    suspend fun login(@Body userCredentials: UserCredentials): Token

    @POST("/auth/logout")
    suspend fun logout()

    @POST("/auth/refresh")
    suspend fun refresh(@Body token: UUID): Token
}
