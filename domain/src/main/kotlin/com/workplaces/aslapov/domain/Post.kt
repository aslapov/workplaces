package com.workplaces.aslapov.domain

import java.net.URI

data class Post(
    val text: String?,
    val imageUrl: URI?,
    val lon: Double?,
    val lat: Double?,
    val author: User,
    val likes: Int,
    val liked: Boolean,
)
