package com.workplaces.aslapov.domain

data class User(
    val id: String,
    val firstName: String?,
    val lastName: String?,
    val nickName: String?,
    val avatarUrl: String?,
    val birthday: String?
)
