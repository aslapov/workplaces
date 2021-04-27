package com.redmadrobot.aslapov.signup

import com.redmadrobot.aslapov.data.UserRepository
import com.redmadrobot.aslapov.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class SignUpViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {
}
