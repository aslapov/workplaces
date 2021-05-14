package com.workplaces.aslapov.app.newfeed

import androidx.lifecycle.viewModelScope
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.app.base.viewmodel.ErrorMessageEvent
import com.workplaces.aslapov.data.NetworkException
import com.workplaces.aslapov.data.RepositoryInUse
import com.workplaces.aslapov.domain.UserRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

class NewFeedDummyViewModel @Inject constructor(
    @RepositoryInUse private val userRepository: UserRepository
) : BaseViewModel() {

    companion object {
        private const val TAG = "SignUpViewModel"
    }

    fun onLogout() {
        viewModelScope.launch {
            try {
                userRepository.logout()
                navigateTo(NewFeedDummyFragmentDirections.logoutAction())
            } catch (e: NetworkException) {
                eventsQueue.offerEvent(ErrorMessageEvent(e.parseMessage))
            } catch (e: UnknownHostException) {
                Timber.tag(TAG).d(e)
                eventsQueue.offerEvent(ErrorMessageEvent("Проверьте подключение к интернету"))
            }
        }
    }
}
