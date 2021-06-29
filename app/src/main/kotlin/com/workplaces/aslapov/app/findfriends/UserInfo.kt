package com.workplaces.aslapov.app.findfriends

import com.workplaces.aslapov.domain.profile.User

data class UserInfo(
    val id: String,
    val fullName: String,
    val nickName: String,
    val avatarUrl: String?,
)

fun toUserInfo(user: User): UserInfo {
    return UserInfo(
        id = user.id,
        fullName = "${user.firstName} ${user.lastName}",
        nickName = user.nickName.orEmpty(),
        avatarUrl = user.avatarUrl,
    )
}
