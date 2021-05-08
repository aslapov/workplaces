package com.workplaces.aslapov.app.signin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import com.redmadrobot.extensions.lifecycle.Event
import com.redmadrobot.extensions.lifecycle.observe
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.app.base.viewmodel.ErrorMessageEvent
import com.workplaces.aslapov.app.base.viewmodel.MessageEvent
import javax.inject.Inject

class SignInFragment @Inject constructor() : BaseFragment(R.layout.signin_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val signInViewModel: SignInViewModel by viewModels { viewModelFactory }
    private lateinit var signIn: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = view.findViewById<EditText>(R.id.email)
        val password = view.findViewById<EditText>(R.id.password)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val register = view.findViewById<Button>(R.id.doRegister)
        signIn = view.findViewById(R.id.signIn)

        observe(signInViewModel.isNextButtonEnabled, ::renderButton)
        observe(signInViewModel.eventsQueue, ::onEvent)

        email.doAfterTextChanged { signInViewModel.onEmailEntered(it.toString()) }
        password.doAfterTextChanged { signInViewModel.onPasswordEntered(it.toString()) }

        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.login_dest)
        }
        register.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.sign_in_to_sign_up_action)
        )
        signIn.setOnClickListener {
            signInViewModel.onSignInClicked()
        }
    }

    private fun renderButton(isEnabled: Boolean) {
        signIn.isEnabled = isEnabled
    }

    private fun onEvent(event: Event) {
        when (event) {
            is MessageEvent -> showMessage(getString(event.message))
            is ErrorMessageEvent -> showError(getString(event.errorMessage))
            is SignInSuccessEvent -> findNavController().navigate(R.id.sign_in_to_welcome_action)
        }
    }

    private fun showMessage(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    private fun showError(error: String) {
        Snackbar.make(requireView(), error, Snackbar.LENGTH_SHORT).show()
    }
}
