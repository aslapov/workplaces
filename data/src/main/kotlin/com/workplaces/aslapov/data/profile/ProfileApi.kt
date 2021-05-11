package com.workplaces.aslapov.data.profile

import com.workplaces.aslapov.domain.User
import retrofit2.http.*
import java.util.*

interface ProfileApi {
    @GET("me")
    suspend fun getMe(): UserNetwork

    @PATCH("me")
    suspend fun updateMe(user: User): UserNetwork

    @GET("me/friends")
    suspend fun getFriends(): List<UserNetwork>

    @POST("me/friends")
    suspend fun addFriend(@Field("user_id") userId: UUID)

    @DELETE("me/friends/{id}")
    suspend fun deleteFriend(@Path("id") userId: UUID)

    @GET("me/posts")
    suspend fun getPosts(): List<PostResponse>

    @POST("me/posts")
    suspend fun addPost(@Body post: PostRequest): PostResponse
}
