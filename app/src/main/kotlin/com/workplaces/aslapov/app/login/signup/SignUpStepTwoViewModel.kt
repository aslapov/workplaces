package com.workplaces.aslapov.app.login.signup

import androidx.lifecycle.MutableLiveData
import com.redmadrobot.extensions.lifecycle.mapDistinct
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.app.base.viewmodel.MessageEvent
import com.workplaces.aslapov.app.base.viewmodel.delegate
import com.workplaces.aslapov.domain.isBirthdayValid
import com.workplaces.aslapov.domain.isFirstnameValid
import com.workplaces.aslapov.domain.isLastnameValid
import com.workplaces.aslapov.domain.isNicknameValid
import javax.inject.Inject

class SignUpStepTwoViewModel @Inject constructor() : BaseViewModel() {
    private val liveState = MutableLiveData(createInitialState())
    private var state: SignUpTwoViewState by liveState.delegate()
    val isRegisterButtonEnabled = liveState.mapDistinct { it.isRegisterButtonEnabled }
    fun onFirstNameEntered(firstName: String) {
        if (isFirstnameValid(firstName)) {
            state = state.copy(firstName = firstName, isFirstNameValid = true)
        } else {
            state = state.copy(firstName = firstName, isFirstNameValid = false)
            eventsQueue.offerEvent(MessageEvent(R.string.sign_up_firstname_error))
        }
        checkRegisterButtonEnable()
    }

    fun onLastNameEntered(lastName: String) {
        if (isLastnameValid(lastName)) {
            state = state.copy(lastName = lastName, isLastNameValid = true)
        } else {
            state = state.copy(lastName = lastName, isLastNameValid = false)
            eventsQueue.offerEvent(MessageEvent(R.string.sign_up_lastname_error))
        }
        checkRegisterButtonEnable()
    }

    fun onNickNameEntered(nickName: String) {
        if (isNicknameValid(nickName)) {
            state = state.copy(nickName = nickName, isNickNameValid = true)
        } else {
            state = state.copy(nickName = nickName, isNickNameValid = false)
            eventsQueue.offerEvent(MessageEvent(R.string.sign_up_nickname_error))
        }
        checkRegisterButtonEnable()
    }

    fun onBirthDayEntered(birthDay: String) {
        if (isBirthdayValid(birthDay)) {
            state = state.copy(birthDay = birthDay, isBirthDayValid = true)
        } else {
            state = state.copy(birthDay = birthDay, isBirthDayValid = false)
            eventsQueue.offerEvent(MessageEvent(R.string.sign_up_birthday_error))
        }
        checkRegisterButtonEnable()
    }
    fun onBackClicked() {
        navigateTo(SignUpStepTwoFragmentDirections.signUpToStepOneAction())
    }

    private fun createInitialState(): SignUpTwoViewState {
        return SignUpTwoViewState(
            firstName = "",
            isFirstNameValid = false,
            lastName = "",
            isLastNameValid = false,
            nickName = "",
            isNickNameValid = false,
            birthDay = "",
            isBirthDayValid = false,
            isRegisterButtonEnabled = false,
        )
    }

    private fun checkRegisterButtonEnable() {
        val isRegisterButtonEnabled = state.isFirstNameValid &&
            state.isLastNameValid &&
            state.isNickNameValid &&
            state.isBirthDayValid
        state = state.copy(isRegisterButtonEnabled = isRegisterButtonEnabled)
    }
}

data class SignUpTwoViewState(
    val firstName: String,
    val isFirstNameValid: Boolean,
    val lastName: String,
    val isLastNameValid: Boolean,
    val nickName: String,
    val isNickNameValid: Boolean,
    val birthDay: String,
    val isBirthDayValid: Boolean,
    val isRegisterButtonEnabled: Boolean,
)
