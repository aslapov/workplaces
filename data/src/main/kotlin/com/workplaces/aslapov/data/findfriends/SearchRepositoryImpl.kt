package com.workplaces.aslapov.data.findfriends

import com.workplaces.aslapov.data.findfriends.network.SearchApi
import com.workplaces.aslapov.domain.findfriends.SearchRepository
import com.workplaces.aslapov.domain.profile.User
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi,
) : SearchRepository {

    override suspend fun findFriends(searchWord: String): List<User> {
        return searchApi.findFriends(searchWord)
    }
}
