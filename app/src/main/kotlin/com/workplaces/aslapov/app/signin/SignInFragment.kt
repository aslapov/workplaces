package com.workplaces.aslapov.app.signin

import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import javax.inject.Inject
import javax.inject.Provider

class SignInFragment @Inject constructor(
    viewModelProvide: Provider<SignInViewModel>
) : BaseFragment(R.layout.fragment_signin) {
}
