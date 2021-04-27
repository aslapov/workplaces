package com.redmadrobot.aslapov.profile

import java.util.*

data class User(
    val id: String,
    val firstName: String?,
    val lastName: String?,
    val nickName: String?,
    val avatarUrl: String?,
    val birthday: String?
)
