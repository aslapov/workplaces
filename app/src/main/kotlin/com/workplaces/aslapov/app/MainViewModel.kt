package com.workplaces.aslapov.app

import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.domain.UserRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {
    fun isUserLoggedIn(): Boolean = userRepository.isUserLoggedIn()
}
