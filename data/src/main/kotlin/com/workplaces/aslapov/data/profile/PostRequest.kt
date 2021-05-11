package com.workplaces.aslapov.data.profile

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostRequest(
    val text: String?,
    @Json(name = "image_file") val imageFile: String?,
    val lon: Double?,
    val lat: Double?,
)
