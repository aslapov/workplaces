package com.workplaces.aslapov.app.profile

import androidx.lifecycle.viewModelScope
import com.redmadrobot.extensions.lifecycle.mapDistinct
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.app.base.viewmodel.MessageEvent
import com.workplaces.aslapov.app.base.viewmodel.NavigateUp
import com.workplaces.aslapov.domain.profile.*
import com.workplaces.aslapov.domain.util.dateTimeFormatter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ProfileEditViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : BaseViewModel<ProfileEditViewState>() {

    val firstName = viewState.mapDistinct { it.firstName }
    val lastName = viewState.mapDistinct { it.lastName }
    val nickName = viewState.mapDistinct { it.nickName }
    val birthDay = viewState.mapDistinct { it.birthDay }
    val isSaveButtonEnabled = viewState.mapDistinct { it.isSaveButtonEnabled }
    val isLoading = viewState.mapDistinct { it.isLoading }

    private lateinit var user: User

    init {
        observeViewState()
    }

    fun onFirstNameEntered(firstName: String) {
        state = if (isFirstnameValid(firstName)) {
            state.copy(firstName = ProfileFieldState(firstName, true, null))
        } else {
            state.copy(firstName = ProfileFieldState(firstName, false, R.string.profile_edit_firstname_error))
        }
        checkSaveButtonEnable()
    }

    fun onLastNameEntered(lastName: String) {
        state = if (isLastnameValid(lastName)) {
            state.copy(lastName = ProfileFieldState(lastName, true, null))
        } else {
            state.copy(lastName = ProfileFieldState(lastName, false, R.string.profile_edit_lastname_error))
        }
        checkSaveButtonEnable()
    }

    fun onNickNameEntered(nickName: String) {
        state = if (isNicknameValid(nickName)) {
            state.copy(nickName = ProfileFieldState(nickName, true, null))
        } else {
            state.copy(nickName = ProfileFieldState(nickName, false, R.string.profile_edit_nickname_error))
        }
        checkSaveButtonEnable()
    }

    fun onBirthDayEntered(birthDay: String) {
        state = if (isBirthdayValid(birthDay)) {
            state.copy(birthDay = ProfileFieldState(birthDay, true, null))
        } else {
            state.copy(birthDay = ProfileFieldState(birthDay, false, R.string.profile_edit_birthday_error))
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
                avatarUrl = user.avatarUrl
            )
            try {
                profileUseCase.updateProfile(user)
                eventsQueue.offerEvent(NavigateUp)
            } catch (e: ProfileException) {
                eventsQueue.offerEvent(MessageEvent(e.messageId))
            } finally {
                state = state.copy(isLoading = false)
            }
        }
    }

    private fun observeViewState() {
        viewModelScope.launch {
            profileUseCase.getCurrentProfile().collect {
                try {
                    user = it
                    val firstname = user.firstName
                    val lastName = user.lastName
                    val nickName = user.nickName.orEmpty()
                    val birthDay = user.birthday.format(dateTimeFormatter)

                    viewState.value = ProfileEditViewState(
                        firstName = ProfileFieldState(firstname, isFirstnameValid(firstname), null),
                        lastName = ProfileFieldState(lastName, isLastnameValid(lastName), null),
                        nickName = ProfileFieldState(nickName, isNicknameValid(nickName), null),
                        birthDay = ProfileFieldState(birthDay, isBirthdayValid(birthDay), null),
                        isSaveButtonEnabled = false,
                        isLoading = false,
                    )
                } catch (e: ProfileException) {
                    eventsQueue.offerEvent(MessageEvent(e.messageId))
                }
            }
        }
    }

    private fun checkSaveButtonEnable() {
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
    val errorId: Int?,
)
