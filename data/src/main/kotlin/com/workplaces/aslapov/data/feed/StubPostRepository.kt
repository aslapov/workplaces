package com.workplaces.aslapov.data.feed

import com.workplaces.aslapov.domain.feed.Post
import com.workplaces.aslapov.domain.feed.PostRepository
import com.workplaces.aslapov.domain.profile.User
import com.workplaces.aslapov.domain.util.dateTimeFormatter
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class StubPostRepository @Inject constructor() : PostRepository {

    companion object {
        private const val POSTS_COUNT = 15
        private const val MOSCOW_LON = 37.36
        private const val MOSCOW_LAT = 55.45
        private const val LIKES_COUNT = 15
        private const val LIKED_INTERVAL = 3
    }

    private val user = User(
        id = UUID.randomUUID().toString(),
        firstName = "Egor",
        lastName = "Aslapov",
        nickName = "Egorius",
        avatarUrl = null,
        birthDay = LocalDate.parse("1994-02-19", dateTimeFormatter),
    )

    private val _posts = mutableListOf<Post>()
    private val posts: List<Post> = _posts

    init {
        for (i in 1..POSTS_COUNT) {
            _posts.add(
                Post(
                    id = i.toString(),
                    text = "Soprano, we like to keep it on a high note. It's levels to it, you and I know",
                    imageUrl = null,
                    lon = MOSCOW_LON,
                    lat = MOSCOW_LAT,
                    author = user,
                    likes = LIKES_COUNT,
                    liked = i % LIKED_INTERVAL == 0,
                )
            )
        }
    }

    override suspend fun getFeed(): List<Post> = posts

    override suspend fun like(postId: String) {
        val postIndex = _posts.indexOfFirst { postItem -> postItem.id == postId }
        val post = _posts[postIndex]
        _posts[postIndex] = post.copy(likes = post.likes + 1, liked = true)
    }

    override suspend fun removeLike(postId: String) {
        val postIndex = _posts.indexOfFirst { postItem -> postItem.id == postId }
        val post = _posts[postIndex]
        _posts[postIndex] = post.copy(likes = post.likes - 1, liked = false)
    }

    override suspend fun getFavoriteFeed(): List<Post> = posts
}
