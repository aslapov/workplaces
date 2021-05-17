package com.workplaces.aslapov.data.profile

import com.workplaces.aslapov.domain.User
import com.workplaces.aslapov.domain.UserRepository
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
