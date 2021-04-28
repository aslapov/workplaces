package com.redmadrobot.aslapov.data.remote

import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeAuthApiImpl @Inject constructor() : AuthApi {
    private val suspendTimeMillis = 500L

    override suspend fun register(user: UserCredentials): SignInResponseBody {
        delay(suspendTimeMillis)
        return SignInResponseBody(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF" +
                "0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
            "8feed535-5ca5-464e-862d-0de124800aa3"
        )
    }

    override suspend fun login(user: UserCredentials): SignInResponseBody {
        delay(suspendTimeMillis)
        return SignInResponseBody(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF" +
                "0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
            "8feed535-5ca5-464e-862d-0de124800aa3"
        )
    }

    override suspend fun logout() {
        delay(suspendTimeMillis)
    }
}
