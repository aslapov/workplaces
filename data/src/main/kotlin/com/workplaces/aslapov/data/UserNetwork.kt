package com.workplaces.aslapov.data

data class UserNetwork(
    val id: String,
    val firstName: String,
    val lastName: String,
    val nickName: String?,
    val avatarUrl: String?,
    val birthday: String
)
