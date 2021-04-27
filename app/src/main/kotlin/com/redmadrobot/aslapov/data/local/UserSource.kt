package com.redmadrobot.aslapov.data.local

import com.redmadrobot.aslapov.profile.User

interface UserSource {
    fun getUser(): User?
    fun setUser(user: User)
    fun logout()
}
