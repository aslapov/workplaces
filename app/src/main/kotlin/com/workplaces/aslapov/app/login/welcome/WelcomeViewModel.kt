package com.workplaces.aslapov.app.login.welcome

import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import javax.inject.Inject

class WelcomeViewModel @Inject constructor() : BaseViewModel<Nothing>() {
    fun onGoToFeedClicked() { navigateAction(R.id.to_main_graph_action) }
}
