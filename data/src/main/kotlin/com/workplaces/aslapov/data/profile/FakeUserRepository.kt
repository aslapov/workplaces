package com.workplaces.aslapov.data.profile

import android.graphics.Bitmap
import com.workplaces.aslapov.data.profile.localstore.UserSharedPreferencesSource
import com.workplaces.aslapov.data.profile.network.FakeProfileApi
import com.workplaces.aslapov.domain.feed.Post
import com.workplaces.aslapov.domain.profile.User
import com.workplaces.aslapov.domain.profile.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import javax.inject.Inject

class FakeUserRepository @Inject constructor(
    private val userSource: UserSharedPreferencesSource,
    private val profileApi: FakeProfileApi
) : UserRepository {

    private val _user: MutableStateFlow<User?> = MutableStateFlow(userSource.getUser())
    override val user: StateFlow<User?> = _user

    override suspend fun getCurrentUser(): User {
        return profileApi.getMe()
            .also { saveUser(it) }
    }

    override suspend fun updateUser(
        firstName: String,
        lastName: String,
        nickName: String,
        avatarUrl: String?,
        birthDay: LocalDate,
    ): User {
        return profileApi.updateUser(
            firstName = firstName,
            lastName = lastName,
            nickName = nickName,
            avatarUrl = avatarUrl,
            birthDay = birthDay,
        )
            .also { saveUser(it) }
    }

    override suspend fun getFriends(): List<User> {
        return emptyList()
    }

    override suspend fun addFriend(userId: String) {
        profileApi.addFriend()
    }

    override suspend fun deleteFriend(userId: String) {
        profileApi.deleteFriend()
    }

    override suspend fun getPosts(): List<Post> {
        return emptyList()
    }

    override suspend fun addPost(text: String?, imageFile: Bitmap?, lon: Double?, lat: Double?): Post {
        return profileApi.addPost(text, lon, lat)
    }

    override fun logout() {
        userSource.setUser(null)
        _user.value = null
    }

    private fun saveUser(user: User) {
        userSource.setUser(user)
        _user.value = user
    }
}
