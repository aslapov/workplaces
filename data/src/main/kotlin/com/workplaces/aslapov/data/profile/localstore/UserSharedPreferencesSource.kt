package com.workplaces.aslapov.data.profile.localstore

import android.content.Context
import androidx.core.content.edit
import com.workplaces.aslapov.data.profile.network.model.UserNetwork
import com.workplaces.aslapov.domain.util.dateTimeFormatter
import java.time.LocalDate
import javax.inject.Inject

class UserSharedPreferencesSource @Inject constructor(context: Context) {

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

    fun getUser(): UserNetwork? {
        val id = sharedPreferences.getString(ID_KEY, null)
        val firstName = sharedPreferences.getString(FIRSTNAME_KEY, null)
        val lastName = sharedPreferences.getString(LASTNAME_KEY, null)
        val nickName = sharedPreferences.getString(NICKNAME_KEY, null)
        val avatarUrl = sharedPreferences.getString(AVATAR_URL_KEY, null)
        val birthday = sharedPreferences.getString(BIRTHDAY_KEY, null)

        return if (id != null) {
            UserNetwork(
                id = id,
                firstName = firstName ?: "Unknown",
                lastName = lastName ?: "Unknown",
                nickName = nickName,
                avatarUrl = avatarUrl,
                birthday = LocalDate.parse(birthday, dateTimeFormatter)
            )
        } else {
            null
        }
    }

    fun setUser(user: UserNetwork) {
        sharedPreferences.edit {
            putString(ID_KEY, user.id)
            putString(FIRSTNAME_KEY, user.firstName)
            putString(LASTNAME_KEY, user.lastName)
            putString(NICKNAME_KEY, user.nickName)
            putString(AVATAR_URL_KEY, user.avatarUrl)
            putString(BIRTHDAY_KEY, user.birthday.format(dateTimeFormatter))

            apply()
        }
    }

    fun logout() {
        sharedPreferences.edit {
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