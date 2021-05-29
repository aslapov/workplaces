package com.workplaces.aslapov.data.feed.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.workplaces.aslapov.data.profile.network.model.UserNetwork
import com.workplaces.aslapov.data.profile.network.model.toUser
import com.workplaces.aslapov.domain.feed.Post

@JsonClass(generateAdapter = true)
data class PostResponse(
    val id: String,
    val text: String?,
    @Json(name = "image_url") val imageUrl: String?,
    val lon: Double?,
    val lat: Double?,
    val author: UserNetwork,
    val likes: Int,
    val liked: Boolean,
)

fun PostResponse.toPost(): Post {
    return Post(
        id = this.id,
        text = this.text,
        imageUrl = this.imageUrl,
        lon = this.lon,
        lat = this.lat,
        author = this.author.toUser(),
        likes = this.likes,
        liked = this.liked,
    )
}
