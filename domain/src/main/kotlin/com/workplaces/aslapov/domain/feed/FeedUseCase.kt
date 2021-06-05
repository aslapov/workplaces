package com.workplaces.aslapov.domain.feed

import com.workplaces.aslapov.domain.ErrorCode
import com.workplaces.aslapov.domain.NetworkException
import com.workplaces.aslapov.domain.R
import com.workplaces.aslapov.domain.di.RepositoryInUse
import com.workplaces.aslapov.domain.profile.ProfileException
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLPeerUnverifiedException

class FeedUseCase @Inject constructor(
    @RepositoryInUse private val postRepository: PostRepository,
) {

    companion object {
        private const val TAG = "FeedUseCase"
    }

    @Suppress("TooGenericExceptionCaught")
    suspend fun getFeed(): List<Post> {
        return try {
            postRepository.getFeed()
        } catch (error: Throwable) {
            Timber.tag(TAG).d(error)
            throw FeedException(getErrorMessageId(error))
        }
    }

    @Suppress("TooGenericExceptionCaught")
    suspend fun like(post: Post) {
        try {
            postRepository.like(post)
        } catch (error: Throwable) {
            Timber.tag(TAG).d(error)
            throw ProfileException(getErrorMessageId(error))
        }
    }

    @Suppress("TooGenericExceptionCaught")
    suspend fun removeLike(post: Post) {
        try {
            postRepository.removeLike(post)
        } catch (error: Throwable) {
            Timber.tag(TAG).d(error)
            throw ProfileException(getErrorMessageId(error))
        }
    }

    private fun getErrorMessageId(error: Throwable): Int {
        return when (error) {
            is NetworkException -> getExceptionMessageIdByCode(error.code)
            is UnknownHostException -> R.string.feed_network_connection_error
            is SSLPeerUnverifiedException -> R.string.feed_ssl_pinning_failure
            else -> R.string.feed_fail
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
