package com.workplaces.aslapov.domain.newpost

import android.graphics.Bitmap
import com.workplaces.aslapov.domain.R
import com.workplaces.aslapov.domain.di.RepositoryInUse
import com.workplaces.aslapov.domain.profile.UserRepository
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.SSLPeerUnverifiedException

class NewPostUseCase @Inject constructor(
    @RepositoryInUse private val userRepository: UserRepository,
) {

    companion object {
        private const val TAG = "NewPostUseCase"
    }

    @Suppress("TooGenericExceptionCaught")
    suspend fun addNewPost(
        text: String?,
        imageFile: Bitmap?,
        lon: Double?,
        lat: Double?,
    ) {
        try {
            userRepository.addPost(
                text = text,
                imageFile = imageFile,
                lon = lon,
                lat = lat,
            )
        } catch (error: Exception) {
            Timber.tag(TAG).d(error)
            throw NewPostException(getErrorMessageId(error))
        }
    }

    private fun getErrorMessageId(error: Throwable): Int {
        return when (error) {
            is UnknownHostException -> R.string.new_post_network_connection_error
            is SSLPeerUnverifiedException -> R.string.new_post_ssl_pinning_failure
            is SSLHandshakeException -> R.string.new_post_ssl_pinning_failure
            else -> R.string.new_post_fail
        }
    }
}
