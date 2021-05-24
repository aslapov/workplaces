package com.workplaces.aslapov.domain.profile

import kotlinx.coroutines.flow.StateFlow

interface UserRepository {

    val user: StateFlow<User?>

    suspend fun getCurrentUser(): User
    suspend fun updateUser(user: User): User

    fun logout()
}
