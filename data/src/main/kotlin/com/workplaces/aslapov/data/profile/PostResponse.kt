package com.workplaces.aslapov.data.profile

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.net.URI
import java.util.*

@JsonClass(generateAdapter = true)
data class PostResponse(
    val id: UUID,
    val text: String?,
    @Json(name = "image_url") val imageUrl: URI?,
    val lon: Double?,
    val lat: Double?,
    val author: UserNetwork,
    val likes: Int,
    val liked: Boolean,
)
