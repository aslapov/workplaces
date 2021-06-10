package com.workplaces.aslapov.data.profile.localstore

import android.content.SharedPreferences
import androidx.core.content.edit
import com.workplaces.aslapov.domain.profile.User
import com.workplaces.aslapov.domain.util.dateTimeFormatter
import java.time.LocalDate
import javax.inject.Inject

class UserSharedPreferencesSource @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        private const val USER_ID_KEY: String = "USER_ID"
        private const val USER_FIRSTNAME_KEY: String = "USER_FIRSTNAME"
        private const val USER_LASTNAME_KEY: String = "USER_LASTNAME"
        private const val USER_NICKNAME_KEY: String = "USER_NICKNAME"
        private const val USER_AVATAR_URL_KEY: String = "USER_AVATAR_URL"
        private const val USER_BIRTHDAY_KEY: String = "USER_BIRTHDAY"
    }

    fun getUser(): User? {
        val id = sharedPreferences.getString(USER_ID_KEY, null)
        val firstName = sharedPreferences.getString(USER_FIRSTNAME_KEY, null)
        val lastName = sharedPreferences.getString(USER_LASTNAME_KEY, null)
        val nickName = sharedPreferences.getString(USER_NICKNAME_KEY, null)
        val avatarUrl = sharedPreferences.getString(USER_AVATAR_URL_KEY, null)
        val birthday = sharedPreferences.getString(USER_BIRTHDAY_KEY, "1970-01-01")

        return if (id != null) {
            User(
                id = id,
                firstName = firstName ?: "Unknown",
                lastName = lastName ?: "Unknown",
                nickName = nickName,
                avatarUrl = avatarUrl,
                birthDay = LocalDate.parse(birthday, dateTimeFormatter)
            )
        } else {
            null
        }
    }

    fun setUser(user: User?) {
        sharedPreferences.edit {
            putString(USER_ID_KEY, user?.id)
            putString(USER_FIRSTNAME_KEY, user?.firstName)
            putString(USER_LASTNAME_KEY, user?.lastName)
            putString(USER_NICKNAME_KEY, user?.nickName)
            putString(USER_AVATAR_URL_KEY, user?.avatarUrl)
            putString(USER_BIRTHDAY_KEY, user?.let { user.birthDay.format(dateTimeFormatter) })

            apply()
        }
    }
}
