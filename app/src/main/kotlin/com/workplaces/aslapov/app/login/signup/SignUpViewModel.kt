package com.workplaces.aslapov.app.login.signup

import androidx.lifecycle.viewModelScope
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.app.base.viewmodel.ErrorMessageEvent
import com.workplaces.aslapov.data.UserRepositoryImpl
import com.workplaces.aslapov.domain.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(private val userRepository: UserRepositoryImpl) : BaseViewModel() {
    var email: String = ""
        private set
    var password: String = ""
        private set
    var firstname: String = ""
    var lastname: String = ""
    var nickname: String = ""
    var birthday: String = ""

    fun onGoNextClicked(email: String, password: String) {
        this.email = email
        this.password = password
    }

    fun onSignUpClicked(firstname: String, lastname: String, nickname: String, birthday: String) {
        this.nickname = nickname
        this.firstname = firstname
        this.lastname = lastname
        this.birthday = birthday
        viewModelScope.launch {
            when (userRepository.register(email, password)) {
                is ResponseResultSuccess -> updateUser()
                is ResponseResultError -> eventsQueue.offerEvent(ErrorMessageEvent(R.string.sign_up_signup_fail))
            }
        }
    }
    private suspend fun updateUser() {
        val user = User(
            firstName = firstname,
            lastName = lastname,
            nickName = nickname,
            birthday = userBirthdayFormatter.parse(birthday) ?: USER_DEFAULT_BIRTHDAY,
            avatarUrl = null
        )
        when (userRepository.updateUser(user)) {
            is ResponseResultSuccess -> navigateTo(SignUpStepTwoFragmentDirections.signUpToWelcomeAction())
            is ResponseResultError -> eventsQueue.offerEvent(ErrorMessageEvent(R.string.sign_up_signup_fail))
        }
    }
}
