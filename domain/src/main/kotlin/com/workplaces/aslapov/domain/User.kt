package com.workplaces.aslapov.domain

import java.text.SimpleDateFormat
import java.util.*

data class User(
    val firstName: String?,
    val lastName: String?,
    val nickName: String?,
    val avatarUrl: String?,
    val birthday: Date
)

val userBirthdayFormatter = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))

val USER_DEFAULT_BIRTHDAY: Date = GregorianCalendar(1970, 1, 1).time
