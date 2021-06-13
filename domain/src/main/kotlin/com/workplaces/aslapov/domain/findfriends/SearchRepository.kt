package com.workplaces.aslapov.domain.findfriends

import com.workplaces.aslapov.domain.profile.User

interface SearchRepository {
    suspend fun findFriends(searchWord: String): List<User>
}
