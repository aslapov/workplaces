package com.workplaces.aslapov.data.profile

import com.workplaces.aslapov.data.AppCache
import com.workplaces.aslapov.data.profile.network.ProfileApi
import com.workplaces.aslapov.data.profile.network.model.toUser
import com.workplaces.aslapov.domain.profile.User
import com.workplaces.aslapov.domain.profile.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class UserRepositoryImpl @Inject constructor(
    private val profileApi: ProfileApi,
    private val cache: AppCache
) : UserRepository {
    override val user: MutableStateFlow<User?> = MutableStateFlow(cache.getUser())

    override suspend fun getCurrentUser(): User {
        return profileApi.getCurrentUser()
            .toUser()
            .also { saveUser(it) }
    }

    override suspend fun updateUser(user: User): User {
        return profileApi.updateMe(
            firstName = user.firstName,
            lastName = user.lastName,
            nickName = user.nickName,
            avatarUrl = user.avatarUrl,
            birthday = user.birthday
        )
            .toUser()
            .also { saveUser(it) }
    }

    private fun saveUser(user: User) {
        cache.setUser(user)
        this.user.value = user
    }
}
