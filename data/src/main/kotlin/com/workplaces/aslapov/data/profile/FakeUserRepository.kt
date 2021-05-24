package com.workplaces.aslapov.data.profile

import com.workplaces.aslapov.data.profile.localstore.UserSharedPreferencesSource
import com.workplaces.aslapov.data.profile.network.FakeProfileApi
import com.workplaces.aslapov.data.profile.network.model.UserNetwork
import com.workplaces.aslapov.data.profile.network.model.toUser
import com.workplaces.aslapov.domain.profile.User
import com.workplaces.aslapov.domain.profile.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class FakeUserRepository @Inject constructor(
    private val userSource: UserSharedPreferencesSource,
    private val profileApi: FakeProfileApi
) : UserRepository {

    private val _user: MutableStateFlow<User?> = MutableStateFlow(userSource.getUser()?.toUser())
    override val user: StateFlow<User?> = _user

    override suspend fun updateUser(user: User): User {
        return profileApi.updateUser(user)
            .also { saveUser(it) }
            .toUser()
    }

    override suspend fun getCurrentUser(): User {
        return profileApi.getMe()
            .also { saveUser(it) }
            .toUser()
    }

    override fun logout() {
        userSource.logout()
        _user.value = null
    }

    private fun saveUser(userNetwork: UserNetwork) {
        userSource.setUser(userNetwork)
        _user.value = userNetwork.toUser()
    }
}
