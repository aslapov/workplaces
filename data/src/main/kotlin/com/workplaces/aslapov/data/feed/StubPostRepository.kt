package com.workplaces.aslapov.data.feed

import com.workplaces.aslapov.domain.feed.Post
import com.workplaces.aslapov.domain.feed.PostRepository
import com.workplaces.aslapov.domain.profile.User
import com.workplaces.aslapov.domain.util.dateTimeFormatter
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class StubPostRepository @Inject constructor() : PostRepository {

    private val user = User(
        firstName = "Egor",
        lastName = "Aslapov",
        nickName = "Egorius",
        avatarUrl = null,
        birthday = LocalDate.parse("1994-02-19", dateTimeFormatter),
    )

    private val post = Post(
        id = UUID.randomUUID().toString(),
        text = "Soprano, we like to keep it on a high note. It's levels to it, you and I know",
        imageUrl = null,
        lon = 37.36,
        lat = 55.45,
        author = user,
        likes = 15,
        liked = true,
    )

    private val _posts = mutableListOf(post, post, post, post, post, post, post, post, post, post, post, post, post)
    private val posts = _posts

    override suspend fun getFeed(): List<Post> = posts

    override suspend fun like(post: Post) {
        val postIndex = _posts.indexOfFirst { postItem -> postItem.id == post.id }
        _posts[postIndex] = post.copy(likes = post.likes + 1, liked = true)
    }

    override suspend fun removeLike(post: Post) {
        val postIndex = _posts.indexOfFirst { postItem -> postItem.id == post.id }
        _posts[postIndex] = post.copy(likes = post.likes - 1, liked = false)
    }

    override suspend fun getFavoriteFeed(): List<Post> = posts
}
