package com.workplaces.aslapov.app.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.viewModels
import com.redmadrobot.extensions.lifecycle.observe
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.di.DI
import javax.inject.Inject

class LoginFragment @Inject constructor() : BaseFragment(R.layout.login_fragment) {

    private val loginViewModel: LoginViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(loginViewModel.eventsQueue, ::onEvent)

        view.findViewById<Button>(R.id.login_sign_in).setOnClickListener { loginViewModel.onSignInClicked() }
        view.findViewById<Button>(R.id.login_sign_up).setOnClickListener { loginViewModel.onSignUpClicked() }
    }
}
