package com.workplaces.aslapov.domain

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

data class User(
    val firstName: String,
    val lastName: String,
    val nickName: String?,
    val avatarUrl: String?,
    val birthday: LocalDate
)

val userBirthdayFormatter = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))
