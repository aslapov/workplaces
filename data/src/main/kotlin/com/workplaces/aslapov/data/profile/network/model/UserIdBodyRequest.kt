package com.workplaces.aslapov.data.profile.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserIdBodyRequest(
    @Json(name = "user_id") val userId: String,
)
