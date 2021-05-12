package com.workplaces.aslapov.app.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.app.base.viewmodel.ErrorMessageEvent
import com.workplaces.aslapov.data.NetworkException
import com.workplaces.aslapov.domain.UserRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException
import java.time.LocalDate
import java.time.Period
import javax.inject.Inject
import javax.inject.Named

class ProfileViewModel @Inject constructor(
    @Named("Network") private val userRepository: UserRepository
) : BaseViewModel() {

    companion object {
        private const val TAG = "ProfileViewModel"
    }
    private val liveState = MutableLiveData(createInitialState())
    val state: LiveData<ProfileViewState> = liveState
    fun initProfile() {
        viewModelScope.launch {
            try {
                val user = userRepository.getMyUser()
                val firstname = user.firstName
                val lastName = user.lastName
                val nickName = if (user.nickName != null) "@${user.nickName}" else ""
                val period = Period.between(user.birthday, LocalDate.now())
                liveState.value = ProfileViewState(
                    name = "$firstname $lastName",
                    nickName = nickName,
                    age = "${period?.years.toString()} лет",
                )
            } catch (e: NetworkException) {
                eventsQueue.offerEvent(ErrorMessageEvent(e.parseMessage))
            } catch (e: UnknownHostException) {
                Timber.tag(TAG).d(e)
                eventsQueue.offerEvent(ErrorMessageEvent("Проверьте подключение к интернету"))
            }
        }
    }
    fun onEdit() {
        navigateTo(ProfileFragmentDirections.profileToProfileEditAction())
    }

    private fun createInitialState(): ProfileViewState {
        return ProfileViewState(
            name = "",
            nickName = "",
            age = "",
        )
    }
}

data class ProfileViewState(
    val name: String,
    val nickName: String,
    val age: String,
)
