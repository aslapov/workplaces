package com.workplaces.aslapov.domain

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val user: User?

    suspend fun getCurrentUser(): Flow<User>
    suspend fun updateUser(user: User): Flow<User>
}
