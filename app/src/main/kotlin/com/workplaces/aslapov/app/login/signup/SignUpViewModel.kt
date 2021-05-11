package com.workplaces.aslapov.app.login.signup

import androidx.lifecycle.viewModelScope
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.app.base.viewmodel.ErrorMessageEvent
import com.workplaces.aslapov.domain.ResponseResultError
import com.workplaces.aslapov.domain.ResponseResultSuccess
import com.workplaces.aslapov.domain.User
import com.workplaces.aslapov.domain.UserRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Named

class SignUpViewModel @Inject constructor(
    @Named("Network") private val userRepository: UserRepository
) : BaseViewModel() {
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
            birthday = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            avatarUrl = null
        )
        when (userRepository.updateUser(user)) {
            is ResponseResultSuccess -> navigateTo(SignUpStepTwoFragmentDirections.signUpToWelcomeAction())
            is ResponseResultError -> eventsQueue.offerEvent(ErrorMessageEvent(R.string.sign_up_signup_fail))
        }
    }
}
