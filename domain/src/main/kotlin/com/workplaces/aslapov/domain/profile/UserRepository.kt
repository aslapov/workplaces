package com.workplaces.aslapov.domain.profile

import android.graphics.Bitmap
import com.workplaces.aslapov.domain.feed.Post
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

interface UserRepository {

    val user: StateFlow<User?>

    suspend fun getCurrentUser(): User

    suspend fun updateUser(
        firstName: String,
        lastName: String,
        nickName: String,
        avatarUrl: String?,
        birthDay: LocalDate,
    ): User

    suspend fun getFriends(): List<User>
    suspend fun addFriend(userId: String)
    suspend fun deleteFriend(userId: String)
    suspend fun getPosts(): List<Post>

    suspend fun addPost(
        text: String?,
        imageFile: Bitmap?,
        lon: Double?,
        lat: Double?,
    ): Post

    fun logout()
}
