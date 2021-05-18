package com.workplaces.aslapov.domain.profile

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {

    @ExperimentalCoroutinesApi
    val user: StateFlow<User?>

    suspend fun getCurrentUser(): User
    suspend fun updateUser(user: User): User

    fun logout()
}
