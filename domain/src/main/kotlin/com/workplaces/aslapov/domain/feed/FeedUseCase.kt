package com.workplaces.aslapov.domain.feed

import com.workplaces.aslapov.domain.ErrorCode
import com.workplaces.aslapov.domain.NetworkException
import com.workplaces.aslapov.domain.R
import com.workplaces.aslapov.domain.di.Mock
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

class FeedUseCase @Inject constructor(
    @Mock private val postRepository: PostRepository,
) {

    companion object {
        private const val TAG = "FeedUseCase"
    }

    suspend fun getFeed(): List<Post> {
        val posts: List<Post>

        try {
            posts = postRepository.getFeed()
        } catch (e: NetworkException) {
            Timber.tag(TAG).d(e)
            throw FeedException(getExceptionMessageIdByCode(e.code))
        } catch (e: UnknownHostException) {
            Timber.tag(TAG).d(e)
            throw FeedException(R.string.profile_network_connection_error)
        }

        return posts
    }

    suspend fun like(post: Post) {
        try {
            postRepository.like(post)
        } catch (e: NetworkException) {
            Timber.tag(TAG).d(e)
            throw FeedException(getExceptionMessageIdByCode(e.code))
        } catch (e: UnknownHostException) {
            Timber.tag(TAG).d(e)
            throw FeedException(R.string.profile_network_connection_error)
        }
    }

    suspend fun removeLike(post: Post) {
        try {
            postRepository.removeLike(post)
        } catch (e: NetworkException) {
            Timber.tag(TAG).d(e)
            throw FeedException(getExceptionMessageIdByCode(e.code))
        } catch (e: UnknownHostException) {
            Timber.tag(TAG).d(e)
            throw FeedException(R.string.profile_network_connection_error)
        }
    }

    private fun getExceptionMessageIdByCode(code: ErrorCode): Int {
        return when (code) {
            ErrorCode.BAD_FILE_EXTENSION_ERROR -> R.string.feed_bad_file_extension_error
            ErrorCode.FILE_NOT_FOUND_ERROR -> R.string.feed_file_not_found_error
            ErrorCode.SERIALIZATION_ERROR -> R.string.feed_serialization_error
            ErrorCode.INVALID_TOKEN -> R.string.feed_invalid_token
            else -> R.string.feed_fail
        }
    }
}
