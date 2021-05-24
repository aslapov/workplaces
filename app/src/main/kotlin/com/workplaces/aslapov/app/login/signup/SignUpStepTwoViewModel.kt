package com.workplaces.aslapov.app.login.signup

import com.redmadrobot.extensions.lifecycle.mapDistinct
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.domain.profile.isBirthdayValid
import com.workplaces.aslapov.domain.profile.isFirstnameValid
import com.workplaces.aslapov.domain.profile.isLastnameValid
import com.workplaces.aslapov.domain.profile.isNicknameValid
import javax.inject.Inject

class SignUpStepTwoViewModel @Inject constructor() : BaseViewModel<SignUpTwoViewState>() {

    val firstName = viewState.mapDistinct { it.firstName }
    val lastName = viewState.mapDistinct { it.lastName }
    val nickName = viewState.mapDistinct { it.nickName }
    val birthDay = viewState.mapDistinct { it.birthDay }
    val isRegisterButtonEnabled = viewState.mapDistinct {
        it.firstName.isValid && it.lastName.isValid && it.nickName.isValid && it.birthDay.isValid
    }

    init {
        viewState.value = createInitialState()
    }

    fun onFirstNameEntered(firstName: String) {
        state = if (isFirstnameValid(firstName)) {
            state.copy(firstName = SignUpTwoFieldState(firstName, true, null))
        } else {
            state.copy(firstName = SignUpTwoFieldState(firstName, false, R.string.sign_up_firstname_error))
        }
    }

    fun onLastNameEntered(lastName: String) {
        state = if (isLastnameValid(lastName)) {
            state.copy(lastName = SignUpTwoFieldState(lastName, true, null))
        } else {
            state.copy(lastName = SignUpTwoFieldState(lastName, false, R.string.sign_up_lastname_error))
        }
    }

    fun onNickNameEntered(nickName: String) {
        state = if (isNicknameValid(nickName)) {
            state.copy(nickName = SignUpTwoFieldState(nickName, true, null))
        } else {
            state.copy(nickName = SignUpTwoFieldState(nickName, false, R.string.sign_up_nickname_error))
        }
    }

    fun onBirthDayEntered(birthDay: String) {
        state = if (isBirthdayValid(birthDay)) {
            state.copy(birthDay = SignUpTwoFieldState(birthDay, true, null))
        } else {
            state.copy(birthDay = SignUpTwoFieldState(birthDay, false, R.string.sign_up_birthday_error))
        }
    }

    fun onBackClicked() {
        navigateUp()
    }

    private fun createInitialState(): SignUpTwoViewState {
        return SignUpTwoViewState(
            firstName = SignUpTwoFieldState("", false, null),
            lastName = SignUpTwoFieldState("", false, null),
            nickName = SignUpTwoFieldState("", false, null),
            birthDay = SignUpTwoFieldState("", false, null),
        )
    }
}

data class SignUpTwoViewState(
    val firstName: SignUpTwoFieldState,
    val lastName: SignUpTwoFieldState,
    val nickName: SignUpTwoFieldState,
    val birthDay: SignUpTwoFieldState,
)

data class SignUpTwoFieldState(
    val value: String,
    val isValid: Boolean,
    val errorId: Int?,
)
