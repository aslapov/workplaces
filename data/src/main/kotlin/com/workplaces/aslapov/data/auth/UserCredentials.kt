package com.workplaces.aslapov.data.auth

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserCredentials(
    val email: String,
    val password: String
)
