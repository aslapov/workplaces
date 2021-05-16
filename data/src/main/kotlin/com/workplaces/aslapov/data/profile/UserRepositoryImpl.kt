package com.workplaces.aslapov.data.profile

import com.workplaces.aslapov.domain.User
import com.workplaces.aslapov.domain.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val profileApi: ProfileApi,
    private val cache: AppCache
) : UserRepository {

    override var user: User? = cache.getUser()

    override suspend fun getCurrentUser(): Flow<User> {
        return flow {
            val user = profileApi.getCurrentUser()
                .toUser()
                .also { saveUser(it) }
            emit(user)
        }
    }

    override suspend fun updateUser(user: User): Flow<User> {
        return flow {
            val updatedUser = profileApi.updateMe(
                firstName = user.firstName,
                lastName = user.lastName,
                nickName = user.nickName,
                avatarUrl = user.avatarUrl,
                birthday = user.birthday
            )
                .toUser()
                .also { saveUser(it) }

            emit(updatedUser)
        }
    }

    private fun saveUser(user: User) {
        this.user = user
        cache.setUser(user)
    }
}
