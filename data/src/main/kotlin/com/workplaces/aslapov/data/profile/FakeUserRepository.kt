package com.workplaces.aslapov.data.profile

import com.workplaces.aslapov.data.auth.FakeAuthApi
import com.workplaces.aslapov.domain.User
import com.workplaces.aslapov.domain.UserRepository
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeUserRepository @Inject constructor(
    private val userSource: UserSharedPreferencesSource,
    private val authApi: FakeAuthApi,
    private val profileApi: FakeProfileApi
) : UserRepository {
    override var user: User? = null

    override var accessToken: String? = null

    override var refreshToken: UUID? = null

    init {
        user = userSource.getUser()?.toUser()
    }

    override fun isUserLoggedIn(): Boolean = user != null

    override suspend fun register(email: String, password: String) {
        authApi.register()
        saveUser()
    }

    override suspend fun login(email: String, password: String) {
        authApi.login()
        saveUser()
    }

    override suspend fun updateUser(user: User) {
        val updatedUser = profileApi.updateUser(user)
        saveUser(updatedUser)
    }

    override suspend fun logout() {
        authApi.logout()
        userSource.logout()
        user = null
    }

    override suspend fun refreshToken(): String {
        return ""
    }

    override suspend fun getMyUser(): User {
        return authApi.getMyUser()
    }

    private suspend fun saveUser() {
        val me: UserNetwork = profileApi.getMe()
        saveUser(me)
    }
    private fun saveUser(userNetwork: UserNetwork) {
        userSource.setUser(userNetwork)
        user = userNetwork.toUser()
    }
}

private fun UserNetwork.toUser(): User {
    return User(
        firstName = this.firstName,
        lastName = this.lastName,
        nickName = this.nickName,
        avatarUrl = this.avatarUrl,
        birthday = this.birthday
    )
}
