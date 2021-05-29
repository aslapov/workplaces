package com.workplaces.aslapov.domain.feed

import com.workplaces.aslapov.domain.profile.User

data class Post(
    val id: String,
    val text: String?,
    val imageUrl: String?,
    val lon: Double?,
    val lat: Double?,
    val author: User,
    val likes: Int,
    val liked: Boolean,
)
