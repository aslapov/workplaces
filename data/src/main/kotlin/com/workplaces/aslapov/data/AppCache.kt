package com.workplaces.aslapov.data

import com.workplaces.aslapov.domain.profile.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppCache @Inject constructor() {

    private val map = mutableMapOf<String, Any?>()

    fun getUser(): User? = map[KEY_USER] as? User

    fun setUser(user: User?) {
        map[KEY_USER] = user
    }

    companion object {
        private const val KEY_USER = "user"
    }
}
