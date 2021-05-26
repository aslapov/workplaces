package com.workplaces.aslapov.data.feed

import com.workplaces.aslapov.data.profile.network.model.PostResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FeedApi {
    @GET("feed")
    suspend fun getFeed(): List<PostResponse>

    @POST("feed/{id}/like")
    suspend fun like(@Path("id") id: String)

    @DELETE("feed/{id}/like")
    suspend fun removeLike(@Path("id") id: String)

    @GET("feed/favorite")
    suspend fun getLikedFeed(): List<PostResponse>
}
