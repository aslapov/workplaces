package com.workplaces.aslapov.app.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.redmadrobot.extensions.lifecycle.observe
import com.redmadrobot.extensions.viewbinding.viewBinding
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.databinding.LoginFragmentBinding
import com.workplaces.aslapov.di.DI

class LoginFragment : BaseFragment(R.layout.login_fragment) {

    private val loginViewModel: LoginViewModel by viewModels { viewModelFactory }
    private val binding: LoginFragmentBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(loginViewModel.eventsQueue, ::onEvent)

        binding.apply {
            loginSignIn.setOnClickListener { loginViewModel.onSignInClicked() }
            loginSignUp.setOnClickListener { loginViewModel.onSignUpClicked() }
        }
    }
}
