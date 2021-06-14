package com.workplaces.aslapov.domain.feed

interface PostRepository {

    suspend fun getFeed(): List<Post>
    suspend fun like(postId: String)
    suspend fun removeLike(postId: String)
    suspend fun getFavoriteFeed(): List<Post>
}
