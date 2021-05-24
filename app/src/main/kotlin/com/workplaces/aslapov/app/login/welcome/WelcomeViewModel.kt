package com.workplaces.aslapov.app.login.welcome

import com.workplaces.aslapov.RootGraphDirections
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import javax.inject.Inject

class WelcomeViewModel @Inject constructor() : BaseViewModel<Nothing>() {

    fun onGoToFeedClicked() {
        navigateTo(RootGraphDirections.toMainGraphAction())
    }
}
