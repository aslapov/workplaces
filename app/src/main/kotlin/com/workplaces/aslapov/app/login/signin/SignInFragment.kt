package com.workplaces.aslapov.app.login.signin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
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
    private lateinit var signIn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = view.findViewById<EditText>(R.id.sign_in_email)
        val password = view.findViewById<EditText>(R.id.sign_in_password)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.sign_in_toolbar)
        val register = view.findViewById<Button>(R.id.sign_in_do_register)
        signIn = view.findViewById(R.id.sign_in)

        observe(signInViewModel.isNextButtonEnabled, ::onNextButtonEnableChanged)
        observe(signInViewModel.eventsQueue, ::onEvent)

        email.doAfterTextChanged { signInViewModel.onEmailEntered(it.toString()) }
        password.doAfterTextChanged { signInViewModel.onPasswordEntered(it.toString()) }

        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.sign_in_to_login_action)
        }
        register.setOnClickListener {
            signInViewModel.onRegisterClicked()
        }
        signIn.setOnClickListener {
            signInViewModel.onSignInClicked()
        }
    }

    private fun onNextButtonEnableChanged(isEnabled: Boolean) {
        signIn.isEnabled = isEnabled
    }
}
