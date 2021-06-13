package com.workplaces.aslapov.domain.findfriends

import com.workplaces.aslapov.domain.R
import com.workplaces.aslapov.domain.di.RepositoryInUse
import com.workplaces.aslapov.domain.profile.User
import com.workplaces.aslapov.domain.profile.UserRepository
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.SSLPeerUnverifiedException

class FindFriendsUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
    @RepositoryInUse private val userRepository: UserRepository,
) {

    companion object {
        private const val TAG = "FindFriendsUseCase"
    }

    @Suppress("TooGenericExceptionCaught")
    suspend fun findFriends(searchWord: String): List<User> {
        return try {
            searchRepository.findFriends(searchWord)
        } catch (error: Exception) {
            Timber.tag(TAG).d(error)
            throw FindFriendsException(getErrorMessageId(error))
        }
    }

    @Suppress("TooGenericExceptionCaught")
    suspend fun addFriend(userId: String) {
        try {
            userRepository.addFriend(userId)
        } catch (error: Exception) {
            Timber.tag(TAG).d(error)
            throw FindFriendsException(getErrorMessageId(error))
        }
    }

    private fun getErrorMessageId(error: Throwable): Int {
        return when (error) {
            is UnknownHostException -> R.string.find_friends_network_connection_error
            is SSLPeerUnverifiedException -> R.string.find_friends_ssl_pinning_failure
            is SSLHandshakeException -> R.string.find_friends_ssl_pinning_failure
            else -> R.string.find_friends_fail
        }
    }
}
