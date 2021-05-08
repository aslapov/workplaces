package com.workplaces.aslapov.app.login.welcome

import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import javax.inject.Inject

class WelcomeViewModel @Inject constructor() : BaseViewModel() {
    fun onGoToFeedClicked() {
        navigateTo(WelcomeFragmentDirections.loginToMainAction())
    }
}
