package com.redmadrobot.aslapov.signin

import com.redmadrobot.aslapov.data.UserRepository
import com.redmadrobot.aslapov.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class SignInViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel()
