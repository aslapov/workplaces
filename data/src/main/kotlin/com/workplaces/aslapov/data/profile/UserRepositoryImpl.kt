package com.workplaces.aslapov.data.profile

import com.workplaces.aslapov.data.profile.localstore.UserSharedPreferencesSource
import com.workplaces.aslapov.data.profile.network.ProfileApi
import com.workplaces.aslapov.data.profile.network.model.UserIdBodyRequest
import com.workplaces.aslapov.data.util.extensions.checkIsSuccessful
import com.workplaces.aslapov.domain.ErrorCode
import com.workplaces.aslapov.domain.NetworkException
import com.workplaces.aslapov.domain.feed.Post
import com.workplaces.aslapov.domain.profile.User
import com.workplaces.aslapov.domain.profile.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.HttpException
import java.time.LocalDate
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

    override suspend fun getFriends(): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun addFriend(userId: String) {
        try {
            profileApi.addFriend(UserIdBodyRequest(userId))
                .checkIsSuccessful()
        } catch (e: HttpException) {
            val exception = NetworkException(e.message(), ErrorCode.GENERIC_ERROR, e.cause)
            throw exception
        }
    }

    override suspend fun deleteFriend(userId: String) {
        try {
            profileApi.deleteFriend(userId)
                .checkIsSuccessful()
        } catch (e: HttpException) {
            val exception = NetworkException(e.message(), ErrorCode.GENERIC_ERROR, e.cause)
            throw exception
        }
    }

    override suspend fun getPosts(): List<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun addPost(text: String?, imageFile: String?, lon: Double?, lat: Double?): Post {
        TODO("Not yet implemented")
    }

    override fun logout() {
        saveUser(null)
    }

    private fun saveUser(user: User?) {
        userSource.setUser(user)
        _user.value = user
    }
}
