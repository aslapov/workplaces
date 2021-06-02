package com.workplaces.aslapov.domain.profile

import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

interface UserRepository {

    val user: StateFlow<User?>

    suspend fun getCurrentUser(): User
    suspend fun updateUser(
        firstName: String,
        lastName: String,
        nickName: String,
        avatarUrl: String?,
        birthDay: LocalDate,
    ): User
    fun logout()
}
