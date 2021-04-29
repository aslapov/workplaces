package com.redmadrobot.aslapov.data.remote

import com.redmadrobot.aslapov.profile.User
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeProfileApiImpl @Inject constructor() : ProfileApi {
    private val suspendTimeMillis = 500L

    override suspend fun getMe(): User {
        delay(suspendTimeMillis)
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
