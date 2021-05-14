package com.workplaces.aslapov.app.login.signin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.redmadrobot.extensions.lifecycle.observe
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.di.DI
import javax.inject.Inject

class SignInFragment @Inject constructor() : BaseFragment(R.layout.signin_fragment) {

    private val signInViewModel: SignInViewModel by viewModels { viewModelFactory }

    private val email: EditText get() = requireView().findViewById(R.id.sign_in_email)
    private val password: EditText get() = requireView().findViewById(R.id.sign_in_password)
    private val toolbar: MaterialToolbar get() = requireView().findViewById(R.id.sign_in_toolbar)
    private val register: Button get() = requireView().findViewById(R.id.sign_in_do_register)
    private val signIn: Button get() = requireView().findViewById(R.id.sign_in)
    private val spinner: ProgressBar get() = requireView().findViewById(R.id.sign_in_spinner)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(signInViewModel.isNextButtonEnabled, ::onNextButtonEnableChanged)
        observe(signInViewModel.isLoading, ::onLoading)
        observe(signInViewModel.eventsQueue, ::onEvent)

        email.doAfterTextChanged { signInViewModel.onEmailEntered(it.toString()) }
        password.doAfterTextChanged { signInViewModel.onPasswordEntered(it.toString()) }

        toolbar.setNavigationOnClickListener { findNavController().navigate(R.id.sign_in_to_login_action) }
        register.setOnClickListener { signInViewModel.onRegisterClicked() }
        signIn.setOnClickListener { signInViewModel.onSignInClicked() }
    }

    private fun onNextButtonEnableChanged(isEnabled: Boolean) {
        signIn.isEnabled = isEnabled
    }
    private fun onLoading(isLoading: Boolean) {
        spinner.isVisible = isLoading
        email.isEnabled = !isLoading
        password.isEnabled = !isLoading
        toolbar.isEnabled = !isLoading
        register.isEnabled = !isLoading
        signIn.isEnabled = !isLoading
    }
}
