package com.workplaces.aslapov.app.profile

import androidx.lifecycle.viewModelScope
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.app.base.viewmodel.ErrorMessageEvent
import com.workplaces.aslapov.app.base.viewmodel.MessageEvent
import com.workplaces.aslapov.data.NetworkException
import com.workplaces.aslapov.data.RepositoryInUse
import com.workplaces.aslapov.domain.AuthRepository
import com.workplaces.aslapov.domain.User
import com.workplaces.aslapov.domain.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException
import java.time.LocalDate
import java.time.Period
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ProfileViewModel @Inject constructor(
    @RepositoryInUse private val userRepository: UserRepository,
    @RepositoryInUse private val authRepository: AuthRepository
) : BaseViewModel<ProfileViewState>() {

    companion object {
        private const val TAG = "ProfileViewModel"
    }

    init {
        observeViewState()
    }

    fun onEdit() { navigateTo(ProfileFragmentDirections.profileToProfileEditAction()) }

    fun onLogout() {
        viewModelScope.launch {
            authRepository.logout()
                .collect { navigateAction(R.id.to_auth_graph_action) }
        }
    }

    private fun observeViewState() {
        viewModelScope.launch {
            userRepository.user.collect {
                try {
                    val user = it ?: userRepository.getCurrentUser()
                    createViewStateFromUser(user)
                } catch (e: NetworkException) {
                    Timber.tag(TAG).d(e)
                    eventsQueue.offerEvent(ErrorMessageEvent(e.parseMessage))
                } catch (e: UnknownHostException) {
                    Timber.tag(TAG).d(e)
                    eventsQueue.offerEvent(MessageEvent(R.string.profile_network_connection_error))
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
            age = "${period.years} лет",
        )
    }
}

data class ProfileViewState(
    val name: String,
    val nickName: String,
    val age: String,
)
