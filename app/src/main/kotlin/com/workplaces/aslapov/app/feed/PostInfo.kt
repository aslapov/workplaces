package com.workplaces.aslapov.app.feed

import com.workplaces.aslapov.domain.feed.Post

data class PostInfo(
    val id: String,
    val text: String?,
    val imageUrl: String?,
    val location: String,
    val authorNickName: String?,
    val likes: Int,
    val liked: Boolean,
)

fun toPostInfo(post: Post): PostInfo {
    val location = "${post.lat?.toString() ?: "0.0"}°, ${post.lon?.toString() ?: "0.0"}°"
    val author = post.author.nickName?.let { "@$it" }.orEmpty()

    return PostInfo(
        id = post.id,
        text = post.text,
        imageUrl = post.imageUrl,
        location = location,
        authorNickName = author,
        likes = post.likes,
        liked = post.liked,
    )
}
