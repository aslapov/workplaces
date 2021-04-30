package com.workplaces.aslapov.domain

interface UserSource {
    fun getUser(): User?
    fun setUser(user: User)
    fun logout()
}
