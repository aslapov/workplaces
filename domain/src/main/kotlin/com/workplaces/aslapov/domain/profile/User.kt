package com.workplaces.aslapov.domain.profile

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class User(
    val id: String,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    @Json(name = "nickname") val nickName: String?,
    @Json(name = "avatar_url") val avatarUrl: String?,
    @Json(name = "birth_day") val birthDay: LocalDate,
)
