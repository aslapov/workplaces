package com.workplaces.aslapov.data.auth

import kotlinx.coroutines.delay
import java.util.*
import javax.inject.Inject

private const val SUSPEND_TIME_MILLIS = 500L

class FakeAuthApi @Inject constructor() {

    suspend fun register(): Token {
        delay(SUSPEND_TIME_MILLIS)
        return Token(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF" +
                "0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
            UUID.fromString("8feed535-5ca5-464e-862d-0de124800aa3")
        )
    }

    suspend fun login(): Token {
        delay(SUSPEND_TIME_MILLIS)
        return Token(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF" +
                "0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
            UUID.fromString("8feed535-5ca5-464e-862d-0de124800aa3")
        )
    }

    suspend fun logout() {
        delay(SUSPEND_TIME_MILLIS)
    }
}