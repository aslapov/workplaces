package com.workplaces.aslapov.data.profile

import com.workplaces.aslapov.domain.User
import com.workplaces.aslapov.domain.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeUserRepository @Inject constructor(
    private val userSource: UserSharedPreferencesSource,
    private val profileApi: FakeProfileApi
) : UserRepository {

    override var user: User? = userSource.getUser()?.toUser()

    override suspend fun updateUser(user: User): Flow<User> {
        return flow {
            val updatedUser = profileApi.updateUser(user)
                .also { saveUser(it) }

            emit(updatedUser.toUser())
        }
    }

    override suspend fun getCurrentUser(): Flow<User> {
        return flow {
            val user = profileApi.getMe()
                .also { saveUser(it) }
            emit(user.toUser())
        }
    }

    private fun saveUser(userNetwork: UserNetwork) {
        userSource.setUser(userNetwork)
    }
}
