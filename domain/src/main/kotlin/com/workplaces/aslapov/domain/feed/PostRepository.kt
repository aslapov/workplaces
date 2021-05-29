package com.workplaces.aslapov.domain.feed

interface PostRepository {

    suspend fun getFeed(): List<Post>
    suspend fun like(post: Post)
    suspend fun removeLike(post: Post)
    suspend fun getFavoriteFeed(): List<Post>
}
