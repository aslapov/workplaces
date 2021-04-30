package com.workplaces.aslapov.app

import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import javax.inject.Inject
import javax.inject.Provider

class LoginFragment @Inject constructor(
    viewModelProvide: Provider<LoginFragment>
) : BaseFragment(R.layout.activity_login) {
}
