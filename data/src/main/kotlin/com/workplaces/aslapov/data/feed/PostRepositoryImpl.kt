package com.workplaces.aslapov.data.feed

import com.workplaces.aslapov.data.feed.network.FeedApi
import com.workplaces.aslapov.data.util.extensions.checkIsSuccessful
import com.workplaces.aslapov.domain.ErrorCode
import com.workplaces.aslapov.domain.NetworkException
import com.workplaces.aslapov.domain.feed.Post
import com.workplaces.aslapov.domain.feed.PostRepository
import retrofit2.HttpException
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val feedApi: FeedApi,
) : PostRepository {

    override suspend fun getFeed(): List<Post> = feedApi.getFeed()

    override suspend fun like(postId: String) {
        try {
            feedApi.like(postId)
                .checkIsSuccessful()
        } catch (e: HttpException) {
            val exception = NetworkException(e.message(), ErrorCode.GENERIC_ERROR, e.cause)
            throw exception
        }
    }

    override suspend fun removeLike(postId: String) {
        try {
            feedApi.removeLike(postId)
                .checkIsSuccessful()
        } catch (e: HttpException) {
            val exception = NetworkException(e.message(), ErrorCode.GENERIC_ERROR, e.cause)
            throw exception
        }
    }

    override suspend fun getFavoriteFeed(): List<Post> = feedApi.getFavoriteFeed()
}
