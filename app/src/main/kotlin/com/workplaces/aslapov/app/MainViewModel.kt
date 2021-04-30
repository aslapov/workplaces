package com.workplaces.aslapov.app

import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.data.UserRepositoryImpl
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl
) : BaseViewModel() {
    fun getFirstFragment(): Class<out BaseFragment> {
        return if (userRepository.isUserLoggedIn()) {
            DummyFragment::class.java
        } else {
            LoginFragment::class.java
        }
    }
}
