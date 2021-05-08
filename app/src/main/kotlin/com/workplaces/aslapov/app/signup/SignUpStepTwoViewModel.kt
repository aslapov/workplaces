package com.workplaces.aslapov.app.signup

import androidx.lifecycle.MutableLiveData
import com.redmadrobot.extensions.lifecycle.mapDistinct
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.app.base.viewmodel.ErrorMessageEvent
import com.workplaces.aslapov.app.base.viewmodel.delegate
import javax.inject.Inject

class SignUpStepTwoViewModel @Inject constructor() : BaseViewModel() {
    private val liveState = MutableLiveData(createInitialState())
    private var state: SignUpTwoViewState by liveState.delegate()
    val isRegisterButtonEnabled = liveState.mapDistinct { it.isRegisterButtonEnabled }
    fun onFirstNameEntered(firstName: String) {
        if (firstName.isNotEmpty()) {
            state = state.copy(firstName = firstName, isFirstNameValid = true)
        } else {
            state = state.copy(firstName = firstName, isFirstNameValid = false)
            eventsQueue.offerEvent(ErrorMessageEvent(R.string.sign_up_empty_field_error))
        }
        checkRegisterButtonEnable()
    }

    fun onLastNameEntered(lastName: String) {
        if (lastName.isNotEmpty()) {
            state = state.copy(lastName = lastName, isLastNameValid = true)
        } else {
            state = state.copy(lastName = lastName, isLastNameValid = false)
            eventsQueue.offerEvent(ErrorMessageEvent(R.string.sign_up_empty_field_error))
        }
        checkRegisterButtonEnable()
    }

    fun onNickNameEntered(nickName: String) {
        if (nickName.isNotEmpty()) {
            state = state.copy(nickName = nickName, isNickNameValid = true)
        } else {
            state = state.copy(nickName = nickName, isNickNameValid = false)
            eventsQueue.offerEvent(ErrorMessageEvent(R.string.sign_up_empty_field_error))
        }
        checkRegisterButtonEnable()
    }

    fun onBirthDayEntered(birthDay: String) {
        if (birthDay.isNotEmpty()) {
            state = state.copy(birthDay = birthDay, isBirthDayValid = true)
        } else {
            state = state.copy(birthDay = birthDay, isBirthDayValid = false)
            eventsQueue.offerEvent(ErrorMessageEvent(R.string.sign_up_empty_field_error))
        }
        checkRegisterButtonEnable()
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
            isRegisterButtonEnabled = false
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
    val isRegisterButtonEnabled: Boolean
)
