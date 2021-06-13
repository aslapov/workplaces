package com.workplaces.aslapov.data.findfriends.network

import com.workplaces.aslapov.domain.profile.User
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("search")
    suspend fun findFriends(@Query("user") searchWord: String): List<User>
}
