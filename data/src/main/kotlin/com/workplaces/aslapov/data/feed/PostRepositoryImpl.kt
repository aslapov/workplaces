package com.workplaces.aslapov.data.feed

import com.workplaces.aslapov.data.feed.network.FeedApi
import com.workplaces.aslapov.data.feed.network.model.toPost
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

    override suspend fun getFeed(): List<Post> {
        return feedApi.getFeed()
            .map { postResponse -> postResponse.toPost() }
    }

    override suspend fun like(post: Post) {
        try {
            feedApi.like(post.id)
                .checkIsSuccessful()
        } catch (e: HttpException) {
            val exception = NetworkException(e.message(), ErrorCode.GENERIC_ERROR, e.cause)
            throw exception
        }
    }

    override suspend fun removeLike(post: Post) {
        try {
            feedApi.removeLike(post.id)
                .checkIsSuccessful()
        } catch (e: HttpException) {
            val exception = NetworkException(e.message(), ErrorCode.GENERIC_ERROR, e.cause)
            throw exception
        }
    }

    override suspend fun getFavoriteFeed(): List<Post> {
        return feedApi.getFavoriteFeed()
            .map { postResponse -> postResponse.toPost() }
    }
}
