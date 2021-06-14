package com.workplaces.aslapov.data.profile.network

import com.workplaces.aslapov.domain.feed.Post
import com.workplaces.aslapov.domain.profile.User
import com.workplaces.aslapov.domain.util.dateTimeFormatter
import kotlinx.coroutines.delay
import java.time.LocalDate
import javax.inject.Inject

class FakeProfileApi @Inject constructor() {

    companion object {
        private const val SUSPEND_TIME_MILLIS = 500L
        private const val ID = "63abe7f0-03d5-451b-a744-f517973987db"
    }

    private val mockUser = User(
        id = ID,
        firstName = "Egor",
        lastName = "Aslapov",
        nickName = "egorius",
        avatarUrl = null,
        birthDay = LocalDate.parse("1970-01-01", dateTimeFormatter)
    )

    suspend fun getMe(): User {
        delay(SUSPEND_TIME_MILLIS)
        return mockUser
    }

    suspend fun updateUser(
        firstName: String,
        lastName: String,
        nickName: String,
        avatarUrl: String?,
        birthDay: LocalDate,
    ): User {
        delay(SUSPEND_TIME_MILLIS)
        return User(
            id = ID,
            firstName = firstName,
            lastName = lastName,
            nickName = nickName,
            avatarUrl = avatarUrl,
            birthDay = birthDay
        )
    }

    suspend fun addFriend() {
        delay(SUSPEND_TIME_MILLIS)
    }

    suspend fun deleteFriend() {
        delay(SUSPEND_TIME_MILLIS)
    }

    suspend fun addPost(text: String?, lon: Double?, lat: Double?): Post {
        delay(SUSPEND_TIME_MILLIS)
        return Post(
            id = ID,
            text = text,
            imageUrl = null,
            author = mockUser,
            lon = lon,
            lat = lat,
            likes = 0,
            liked = false,
        )
    }
}
