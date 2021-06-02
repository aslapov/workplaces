package com.workplaces.aslapov.data.profile

import com.workplaces.aslapov.data.AppCache
import com.workplaces.aslapov.data.profile.network.ProfileApi
import com.workplaces.aslapov.domain.profile.User
import com.workplaces.aslapov.domain.profile.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val profileApi: ProfileApi,
    private val cache: AppCache
) : UserRepository {

    private val _user: MutableStateFlow<User?> = MutableStateFlow(cache.getUser())
    override val user: StateFlow<User?> = _user

    override suspend fun getCurrentUser(): User {
        return profileApi.getCurrentUser()
            .also { saveUser(it) }
    }

    override suspend fun updateUser(
        firstName: String,
        lastName: String,
        nickName: String,
        avatarUrl: String?,
        birthDay: LocalDate,
    ): User {
        return profileApi.updateMe(
            firstName = firstName,
            lastName = lastName,
            nickName = nickName,
            avatarUrl = avatarUrl,
            birthDay = birthDay,
        )
            .also { saveUser(it) }
    }

    override fun logout() {
        saveUser(null)
    }

    private fun saveUser(user: User?) {
        cache.setUser(user)
        _user.value = user
    }
}
