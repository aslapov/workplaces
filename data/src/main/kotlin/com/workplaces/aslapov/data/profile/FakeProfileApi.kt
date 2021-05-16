package com.workplaces.aslapov.data.profile

import com.workplaces.aslapov.data.util.dateTimeFormatter
import com.workplaces.aslapov.domain.User
import kotlinx.coroutines.delay
import java.time.LocalDate
import javax.inject.Inject

private const val SUSPEND_TIME_MILLIS = 500L

class FakeProfileApi @Inject constructor() {
    suspend fun getMe(): UserNetwork {
        delay(SUSPEND_TIME_MILLIS)
        return UserNetwork(
            id = "63abe7f0-03d5-451b-a744-f517973987db",
            firstName = "Egor",
            lastName = "Aslapov",
            nickName = null,
            avatarUrl = null,
            birthday = LocalDate.parse("1970-01-01", dateTimeFormatter)
        )
    }

    suspend fun updateUser(user: User): UserNetwork {
        delay(SUSPEND_TIME_MILLIS)
        return UserNetwork(
            id = "63abe7f0-03d5-451b-a744-f517973987db",
            firstName = user.firstName,
            lastName = user.lastName,
            nickName = user.nickName,
            avatarUrl = user.avatarUrl,
            birthday = user.birthday
        )
    }
}
