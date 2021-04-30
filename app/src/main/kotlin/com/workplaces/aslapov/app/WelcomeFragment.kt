package com.workplaces.aslapov.app

import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import javax.inject.Inject
import javax.inject.Provider

class WelcomeFragment @Inject constructor(
    viewModelProvide: Provider<WelcomeFragment>
) : BaseFragment(R.layout.fragment_welcome) {
}
