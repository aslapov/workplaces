package com.workplaces.aslapov.domain.feed

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.workplaces.aslapov.domain.profile.User

@JsonClass(generateAdapter = true)
data class Post(
    val id: String,
    val text: String?,
    @Json(name = "image_url") val imageUrl: String?,
    val lon: Double?,
    val lat: Double?,
    val author: User,
    val likes: Int,
    val liked: Boolean,
)
