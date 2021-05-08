package com.workplaces.aslapov.data

import com.workplaces.aslapov.domain.User
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

private const val SUSPEND_TIME_MILLIS = 500L
private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))

class FakeProfileApi @Inject constructor() {
    suspend fun getMe(): UserNetwork {
        delay(SUSPEND_TIME_MILLIS)
        return UserNetwork(
            id = "63abe7f0-03d5-451b-a744-f517973987db",
            firstName = "Egor",
            lastName = "Aslapov",
            nickName = null,
            avatarUrl = null,
            birthday = "1970-01-01"
        )
    }

    suspend fun updateUser(user: User): UserNetwork {
        delay(SUSPEND_TIME_MILLIS)
        return UserNetwork(
            id = "63abe7f0-03d5-451b-a744-f517973987db",
            firstName = user.firstName ?: "Unknown",
            lastName = user.lastName ?: "Unknown",
            nickName = user.nickName,
            avatarUrl = user.avatarUrl,
            birthday = dateFormatter.format(user.birthday)
        )
    }
}
