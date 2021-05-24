package com.workplaces.aslapov.data.auth.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserCredentialsNetwork(
    val email: String,
    val password: String
)
