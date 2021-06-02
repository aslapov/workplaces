package com.workplaces.aslapov.data.feed.network

import com.workplaces.aslapov.domain.feed.Post
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FeedApi {
    @GET("feed")
    suspend fun getFeed(): List<Post>

    @POST("feed/{id}/like")
    suspend fun like(@Path("id") id: String): Response<Unit>

    @DELETE("feed/{id}/like")
    suspend fun removeLike(@Path("id") id: String): Response<Unit>

    @GET("feed/favorite")
    suspend fun getFavoriteFeed(): List<Post>
}
