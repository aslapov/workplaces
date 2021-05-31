package com.workplaces.aslapov.data.feed

import com.workplaces.aslapov.domain.feed.Post
import com.workplaces.aslapov.domain.feed.PostRepository
import com.workplaces.aslapov.domain.profile.User
import com.workplaces.aslapov.domain.util.dateTimeFormatter
import java.time.LocalDate
import javax.inject.Inject

const val POSTS_COUNT = 15
const val MOSCOW_LON = 37.36
const val MOSCOW_LAT = 55.45
const val LIKES_COUNT = 15
const val LIKED_INTERVAL = 3

class StubPostRepository @Inject constructor() : PostRepository {

    private val user = User(
        firstName = "Egor",
        lastName = "Aslapov",
        nickName = "Egorius",
        avatarUrl = null,
        birthday = LocalDate.parse("1994-02-19", dateTimeFormatter),
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
