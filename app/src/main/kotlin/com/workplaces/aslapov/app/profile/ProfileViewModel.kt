package com.workplaces.aslapov.app.profile

import androidx.lifecycle.viewModelScope
import com.workplaces.aslapov.R
import com.workplaces.aslapov.ResourceProvider
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.app.base.viewmodel.MessageEvent
import com.workplaces.aslapov.domain.profile.ProfileException
import com.workplaces.aslapov.domain.profile.ProfileUseCase
import com.workplaces.aslapov.domain.profile.User
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val resources: ResourceProvider
) : BaseViewModel<ProfileViewState>() {

    init {
        observeViewState()
    }

    fun onEdit() { navigateTo(ProfileFragmentDirections.profileToProfileEditAction()) }

    fun onLogout() {
        viewModelScope.launch { profileUseCase.logout() }
    }

    private fun observeViewState() {
        viewModelScope.launch {
            profileUseCase.getCurrentProfile().collect {
                try {
                    val user = it
                    createViewStateFromUser(user)
                } catch (e: ProfileException) {
                    eventsQueue.offerEvent(MessageEvent(e.messageId))
                }
            }
        }
    }

    private fun createViewStateFromUser(user: User) {
        val firstname = user.firstName
        val lastName = user.lastName
        val nickName = user.nickName?.let { "@${user.nickName}" }.orEmpty()
        val period = Period.between(user.birthday, LocalDate.now())

        viewState.value = ProfileViewState(
            name = "$firstname $lastName",
            nickName = nickName,
            age = resources.getQuantityString(R.plurals.age, period.years, period.years),
        )
    }
}

data class ProfileViewState(
    val name: String,
    val nickName: String,
    val age: String,
)
