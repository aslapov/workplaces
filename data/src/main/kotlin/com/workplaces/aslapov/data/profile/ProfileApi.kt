package com.workplaces.aslapov.data.profile

import retrofit2.Response
import retrofit2.http.*
import java.time.LocalDate
import java.util.*

interface ProfileApi {
    @GET("me")
    suspend fun getMe(): UserNetwork

    @Multipart
    @PATCH("me")
    suspend fun updateMe(
        @Part("first_name") firstName: String,
        @Part("last_name") lastName: String,
        @Part("nickname") nickName: String?,
        @Part("avatar_url") avatarUrl: String?,
        @Part("birth_day") birthday: LocalDate,
    ): UserNetwork

    @GET("me/friends")
    suspend fun getFriends(): List<UserNetwork>

    @POST("me/friends")
    suspend fun addFriend(@Field("user_id") userId: UUID): Response<Unit>

    @DELETE("me/friends/{id}")
    suspend fun deleteFriend(@Path("id") userId: UUID): Response<Unit>

    @GET("me/posts")
    suspend fun getPosts(): List<PostResponse>

    @POST("me/posts")
    suspend fun addPost(@Body post: PostRequest): PostResponse
}
