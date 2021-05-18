package com.workplaces.aslapov.domain.profile

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow

interface UserRepository {

    @ExperimentalCoroutinesApi
    val user: MutableStateFlow<User?>

    suspend fun getCurrentUser(): User
    suspend fun updateUser(user: User): User
}
