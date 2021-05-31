package com.workplaces.aslapov.data.profile

import com.workplaces.aslapov.data.profile.localstore.UserSharedPreferencesSource
import com.workplaces.aslapov.data.profile.network.ProfileApi
import com.workplaces.aslapov.data.profile.network.model.UserNetwork
import com.workplaces.aslapov.data.profile.network.model.toUser
import com.workplaces.aslapov.domain.profile.User
import com.workplaces.aslapov.domain.profile.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val profileApi: ProfileApi,
    private val userSource: UserSharedPreferencesSource,
) : UserRepository {

    private val _user: MutableStateFlow<User?> = MutableStateFlow(userSource.getUser())
    override val user: StateFlow<User?> = _user

    override suspend fun getCurrentUser(): User {
        return profileApi.getCurrentUser()
            .also { saveUser(it) }
            .toUser()
    }

    override suspend fun updateUser(user: User): User {
        return profileApi.updateMe(
            firstName = user.firstName,
            lastName = user.lastName,
            nickName = user.nickName,
            avatarUrl = user.avatarUrl,
            birthday = user.birthday
        )
            .also { saveUser(it) }
            .toUser()
    }

    override fun logout() {
        saveUser(null)
    }

    private fun saveUser(user: UserNetwork?) {
        userSource.setUser(user)
        _user.value = user?.toUser()
    }
}
