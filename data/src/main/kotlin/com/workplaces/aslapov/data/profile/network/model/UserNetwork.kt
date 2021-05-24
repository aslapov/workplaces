package com.workplaces.aslapov.data.profile.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.workplaces.aslapov.domain.profile.User
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class UserNetwork(
    val id: String,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    @Json(name = "nickname") val nickName: String?,
    @Json(name = "avatar_url") val avatarUrl: String?,
    @Json(name = "birth_day") val birthday: LocalDate,
)

fun UserNetwork.toUser(): User {
    return User(
        firstName = this.firstName,
        lastName = this.lastName,
        nickName = this.nickName,
        avatarUrl = this.avatarUrl,
        birthday = this.birthday
    )
}
