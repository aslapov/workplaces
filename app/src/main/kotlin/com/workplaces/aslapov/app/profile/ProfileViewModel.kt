package com.workplaces.aslapov.app.profile

import androidx.lifecycle.viewModelScope
import com.workplaces.aslapov.MainGraphDirections
import com.workplaces.aslapov.R
import com.workplaces.aslapov.ResourceProvider
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.app.base.viewmodel.MessageEvent
import com.workplaces.aslapov.domain.profile.ProfileException
import com.workplaces.aslapov.domain.profile.ProfileUseCase
import com.workplaces.aslapov.domain.profile.User
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.Period
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val resources: ResourceProvider,
) : BaseViewModel<ProfileViewState>() {

    companion object {
        private const val TAG = "ProfileViewModel"
    }

    init {
        createInitialState()
        observeViewState()
    }

    fun onEditClicked() {
        navigateTo(ProfileFragmentDirections.profileToProfileEditAction())
    }

    fun onLogout() {
        viewModelScope.launch { profileUseCase.logout() }
    }

    fun onFindFriendsClicked() {
        navigateTo(MainGraphDirections.toFindFriendsGraph())
    }

    private fun observeViewState() {
        profileUseCase.getCurrentProfile()
            .onEach { user -> createViewStateFromUser(user) }
            .catch { handleError(it) }
            .launchIn(viewModelScope)
    }

    private fun handleError(error: Throwable) {
        Timber.tag(TAG).d(error)
        when (error) {
            is ProfileException -> eventsQueue.offerEvent(MessageEvent(error.messageId))
        }
        state = state.copy(isErrorState = true, isLoading = false)
    }

    private fun createViewStateFromUser(user: User) {
        val firstname = user.firstName
        val lastName = user.lastName
        val nickName = user.nickName?.let { "@${user.nickName}" }.orEmpty()
        val period = Period.between(user.birthDay, LocalDate.now())

        state = ProfileViewState(
            name = "$firstname $lastName",
            nickName = nickName,
            age = resources.getQuantityString(R.plurals.age, period.years, period.years),
            isErrorState = false,
            isLoading = false,
        )
    }

    private fun createInitialState() {
        state = ProfileViewState(
            name = "",
            nickName = "",
            age = "",
            isErrorState = false,
            isLoading = true,
        )
    }
}

data class ProfileViewState(
    val name: String,
    val nickName: String,
    val age: String,
    val isErrorState: Boolean,
    val isLoading: Boolean,
)
