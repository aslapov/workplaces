package com.workplaces.aslapov.data

import com.workplaces.aslapov.domain.User
import kotlinx.coroutines.delay

private const val SUSPEND_TIME_MILLIS = 500L

class FakeProfileApi {
    suspend fun getMe(): User {
        delay(SUSPEND_TIME_MILLIS)
        return User(
            id = "63abe7f0-03d5-451b-a744-f517973987db",
            firstName = "Unknown",
            lastName = "Unknown",
            nickName = null,
            avatarUrl = null,
            birthday = "1970-01-01"
        )
    }
}
