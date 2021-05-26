package com.workplaces.aslapov.data.auth.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RefreshTokenRequest(
    @Json(name = "token") val token: String,
)
