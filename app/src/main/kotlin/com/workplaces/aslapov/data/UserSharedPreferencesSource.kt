package com.workplaces.aslapov.data

import android.content.Context
import com.workplaces.aslapov.domain.User
import com.workplaces.aslapov.domain.UserSource
import javax.inject.Inject

class UserSharedPreferencesSource @Inject constructor(context: Context) : UserSource {

    companion object {
        private const val USER_SHARED_PREFS_FILE: String = "USER_SHARED_PREFS_FILE"
        private const val ID_KEY: String = "ID_KEY"
        private const val FIRSTNAME_KEY: String = "FIRSTNAME_KEY"
        private const val LASTNAME_KEY: String = "LASTNAME_KEY"
        private const val NICKNAME_KEY: String = "NICKNAME_KEY"
        private const val AVATAR_URL_KEY: String = "AVATAR_URL_KEY"
        private const val BIRTHDAY_KEY: String = "BIRTHDAY_KEY"
    }

    private val sharedPreferences = context.getSharedPreferences(USER_SHARED_PREFS_FILE, Context.MODE_PRIVATE)

    override fun getUser(): User? {
        val id = sharedPreferences.getString(ID_KEY, null)
        val firstName = sharedPreferences.getString(FIRSTNAME_KEY, null)
        val lastName = sharedPreferences.getString(LASTNAME_KEY, null)
        val nickName = sharedPreferences.getString(NICKNAME_KEY, null)
        val avatarUrl = sharedPreferences.getString(AVATAR_URL_KEY, null)
        val birthday = sharedPreferences.getString(BIRTHDAY_KEY, null)

        return if (id != null) {
            User(
                id = id,
                firstName = firstName,
                lastName = lastName,
                nickName = nickName,
                avatarUrl = avatarUrl,
                birthday = birthday
            )
        } else {
            null
        }
    }

    override fun setUser(user: User) {
        with(sharedPreferences.edit()) {
            putString(ID_KEY, user.id)
            putString(FIRSTNAME_KEY, user.firstName)
            putString(LASTNAME_KEY, user.lastName)
            putString(NICKNAME_KEY, user.nickName)
            putString(AVATAR_URL_KEY, user.avatarUrl)
            putString(BIRTHDAY_KEY, user.birthday)

            apply()
        }
    }

    override fun logout() {
        with(sharedPreferences.edit()) {
            putString(ID_KEY, null)
            putString(FIRSTNAME_KEY, null)
            putString(LASTNAME_KEY, null)
            putString(NICKNAME_KEY, null)
            putString(AVATAR_URL_KEY, null)
            putString(BIRTHDAY_KEY, null)

            apply()
        }
    }
}
