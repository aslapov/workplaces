package com.redmadrobot.aslapov.data.remote

import com.redmadrobot.aslapov.profile.User

interface ProfileApi {
    suspend fun getMe(): User
}
