package com.workplaces.aslapov.app.newfeed

import androidx.lifecycle.viewModelScope
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.app.base.viewmodel.ErrorMessageEvent
import com.workplaces.aslapov.domain.ResponseResultError
import com.workplaces.aslapov.domain.ResponseResultSuccess
import com.workplaces.aslapov.domain.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class NewFeedDummyViewModel @Inject constructor(
    @Named("Network") private val userRepository: UserRepository
) : BaseViewModel() {

    fun onLogout() {
        viewModelScope.launch {
            when (userRepository.logout()) {
                is ResponseResultSuccess -> navigateTo(NewFeedDummyFragmentDirections.logoutAction())
                is ResponseResultError -> eventsQueue.offerEvent(ErrorMessageEvent(R.string.dummy_logout_error))
            }
        }
    }
}
