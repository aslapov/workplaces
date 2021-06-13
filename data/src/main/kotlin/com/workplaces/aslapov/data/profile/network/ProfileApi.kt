package com.workplaces.aslapov.data.profile.network

import com.workplaces.aslapov.data.profile.network.model.UserIdBodyRequest
import com.workplaces.aslapov.domain.feed.Post
import com.workplaces.aslapov.domain.profile.User
import retrofit2.Response
import retrofit2.http.*
import java.time.LocalDate

interface ProfileApi {
    @GET("me")
    suspend fun getCurrentUser(): User

    @Multipart
    @PATCH("me")
    suspend fun updateMe(
        @Part("first_name") firstName: String,
        @Part("last_name") lastName: String,
        @Part("nickname") nickName: String?,
        @Part("avatar_url") avatarUrl: String?,
        @Part("birth_day") birthDay: LocalDate,
    ): User

    @GET("me/friends")
    suspend fun getFriends(): List<User>

    @POST("me/friends")
    suspend fun addFriend(@Body userId: UserIdBodyRequest): Response<Unit>

    @DELETE("me/friends/{id}")
    suspend fun deleteFriend(@Path("id") userId: String): Response<Unit>

    @GET("me/posts")
    suspend fun getPosts(): List<Post>

    @Multipart
    @POST("me/posts")
    suspend fun addPost(
        @Part("text") text: String?,
        @Part("image_file") imageFile: String?,
        @Part("lon") lon: Double?,
        @Part("lat") lat: Double?,
    ): Post
}
