package com.workplaces.aslapov.data.profile.network

import com.workplaces.aslapov.domain.profile.User
import com.workplaces.aslapov.domain.util.dateTimeFormatter
import kotlinx.coroutines.delay
import java.time.LocalDate
import javax.inject.Inject

class FakeProfileApi @Inject constructor() {

    companion object {
        private const val SUSPEND_TIME_MILLIS = 500L
    }

    suspend fun getMe(): User {
        delay(SUSPEND_TIME_MILLIS)
        return User(
            id = "63abe7f0-03d5-451b-a744-f517973987db",
            firstName = "Egor",
            lastName = "Aslapov",
            nickName = "egorius",
            avatarUrl = null,
            birthDay = LocalDate.parse("1970-01-01", dateTimeFormatter)
        )
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
            id = "63abe7f0-03d5-451b-a744-f517973987db",
            firstName = firstName,
            lastName = lastName,
            nickName = nickName,
            avatarUrl = avatarUrl,
            birthDay = birthDay
        )
    }
}
