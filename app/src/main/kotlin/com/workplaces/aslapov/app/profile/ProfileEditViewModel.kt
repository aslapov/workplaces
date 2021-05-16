package com.workplaces.aslapov.app.profile

import androidx.lifecycle.viewModelScope
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.app.base.viewmodel.ErrorMessageEvent
import com.workplaces.aslapov.app.base.viewmodel.MessageEvent
import com.workplaces.aslapov.app.base.viewmodel.NavigateUp
import com.workplaces.aslapov.data.NetworkException
import com.workplaces.aslapov.data.RepositoryInUse
import com.workplaces.aslapov.data.util.dateTimeFormatter
import com.workplaces.aslapov.domain.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException
import java.time.LocalDate
import javax.inject.Inject

class ProfileEditViewModel @Inject constructor(
    @RepositoryInUse private val userRepository: UserRepository
) : BaseViewModel<ProfileEditViewState>() {

    companion object {
        private const val TAG = "ProfileEditViewModel"
    }

    init {
        viewState.value = createInitialState()
    }

    fun onFirstNameEntered(firstName: String) {
        if (isFirstnameValid(firstName)) {
            state = state.copy(firstName = ProfileFieldState(firstName, true))
        } else {
            state = state.copy(firstName = ProfileFieldState(firstName, false))
            eventsQueue.offerEvent(MessageEvent(R.string.profile_firstname_error))
        }
        checkSaveButtonEnable()
    }

    fun onLastNameEntered(lastName: String) {
        if (isLastnameValid(lastName)) {
            state = state.copy(lastName = ProfileFieldState(lastName, true))
        } else {
            state = state.copy(lastName = ProfileFieldState(lastName, false))
            eventsQueue.offerEvent(MessageEvent(R.string.profile_lastname_error))
        }
        checkSaveButtonEnable()
    }

    fun onNickNameEntered(nickName: String) {
        if (isNicknameValid(nickName)) {
            state = state.copy(nickName = ProfileFieldState(nickName, true))
        } else {
            state = state.copy(nickName = ProfileFieldState(nickName, false))
            eventsQueue.offerEvent(MessageEvent(R.string.profile_nickname_error))
        }
        checkSaveButtonEnable()
    }

    fun onBirthDayEntered(birthDay: String) {
        if (isBirthdayValid(birthDay)) {
            state = state.copy(birthDay = ProfileFieldState(birthDay, true))
        } else {
            state = state.copy(birthDay = ProfileFieldState(birthDay, false))
            eventsQueue.offerEvent(MessageEvent(R.string.profile_birthday_error))
        }
        checkSaveButtonEnable()
    }

    fun onBackClicked() { navigateUp() }

    fun onSaveClicked() {
        viewModelScope.launch {
            val user = User(
                firstName = state.firstName.value,
                lastName = state.lastName.value,
                nickName = state.nickName.value,
                birthday = LocalDate.parse(state.birthDay.value, dateTimeFormatter),
                avatarUrl = userRepository.user?.avatarUrl
            )
            try {
                userRepository.updateUser(user)
                eventsQueue.offerEvent(NavigateUp)
            } catch (e: NetworkException) {
                Timber.tag(TAG).d(e)
                eventsQueue.offerEvent(ErrorMessageEvent(e.parseMessage))
            } catch (e: UnknownHostException) {
                Timber.tag(TAG).d(e)
                eventsQueue.offerEvent(MessageEvent(R.string.profile_network_connecction_error))
            } finally {
                state = state.copy(isLoading = false)
            }
        }
    }

    private fun createInitialState(): ProfileEditViewState {
        val user = userRepository.user
        requireNotNull(user)
        val firstname = user.firstName
        val lastName = user.lastName
        val nickName = user.nickName.orEmpty()
        val birthDay = user.birthday.format(dateTimeFormatter)

        return ProfileEditViewState(
            firstName = ProfileFieldState(firstname, isFirstnameValid(firstname)),
            lastName = ProfileFieldState(lastName, isLastnameValid(lastName)),
            nickName = ProfileFieldState(nickName, isNicknameValid(nickName)),
            birthDay = ProfileFieldState(birthDay, isBirthdayValid(birthDay)),
            isSaveButtonEnabled = false,
            isLoading = false,
        )
    }

    private fun checkSaveButtonEnable() {
        val user = requireNotNull(userRepository.user)

        val isUserFieldsValid = state.firstName.isValid &&
            state.lastName.isValid &&
            state.nickName.isValid &&
            state.birthDay.isValid

        val isUserFieldsChanged = state.firstName.value != user.firstName ||
            state.lastName.value != user.lastName ||
            state.nickName.value != user.nickName ||
            state.birthDay.value != user.birthday.format(dateTimeFormatter)

        state = state.copy(isSaveButtonEnabled = isUserFieldsValid && isUserFieldsChanged)
    }
}

data class ProfileEditViewState(
    val firstName: ProfileFieldState,
    val lastName: ProfileFieldState,
    val nickName: ProfileFieldState,
    val birthDay: ProfileFieldState,
    val isSaveButtonEnabled: Boolean,
    val isLoading: Boolean,
)

data class ProfileFieldState(
    val value: String,
    val isValid: Boolean,
)
