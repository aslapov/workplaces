package com.workplaces.aslapov.domain.profile

import java.time.LocalDate

data class User(
    val firstName: String,
    val lastName: String,
    val nickName: String?,
    val avatarUrl: String?,
    val birthday: LocalDate
)
