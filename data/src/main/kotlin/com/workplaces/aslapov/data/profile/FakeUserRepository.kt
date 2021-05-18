package com.workplaces.aslapov.data.profile

import com.workplaces.aslapov.data.profile.localstore.UserSharedPreferencesSource
import com.workplaces.aslapov.data.profile.network.FakeProfileApi
import com.workplaces.aslapov.data.profile.network.model.UserNetwork
import com.workplaces.aslapov.data.profile.network.model.toUser
import com.workplaces.aslapov.domain.profile.User
import com.workplaces.aslapov.domain.profile.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class FakeUserRepository @Inject constructor(
    private val userSource: UserSharedPreferencesSource,
    private val profileApi: FakeProfileApi
) : UserRepository {

    override var user: MutableStateFlow<User?> = MutableStateFlow(userSource.getUser()?.toUser())

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

    private fun saveUser(userNetwork: UserNetwork) {
        userSource.setUser(userNetwork)
    }
}
