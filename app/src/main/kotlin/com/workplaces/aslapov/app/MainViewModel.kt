package com.workplaces.aslapov.app

import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.domain.UserRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {
    fun getFirstDestinationId(): Int = if (userRepository.isUserLoggedIn()) R.id.dummy_dest else R.id.login_dest
}
