package com.workplaces.aslapov.app.signup

import android.os.Bundle
import android.text.Editable
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
import com.workplaces.aslapov.app.ErrorMessageEvent
import com.workplaces.aslapov.app.MessageEvent
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import javax.inject.Inject

class SignUpStepOneFragment @Inject constructor() : BaseFragment(R.layout.signup_one_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val signUpViewModel: SignUpViewModel by viewModels { viewModelFactory }
    private val signUpStepOneViewModel: SignUpStepOneViewModel by viewModels { viewModelFactory }
    private lateinit var signUpNext: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(signUpStepOneViewModel.isNextButtonEnabled, ::renderButton)
        observe(signUpStepOneViewModel.eventsQueue, ::onEvent)

        val email = view.findViewById<EditText>(R.id.email)
        val password = view.findViewById<EditText>(R.id.password)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val registered = view.findViewById<Button>(R.id.registerAlready)
        signUpNext = view.findViewById(R.id.next)

        email.text = signUpViewModel.email.toEditable()
        password.text = signUpViewModel.password.toEditable()
        signUpNext.isEnabled = signUpViewModel.isUserDataStepFirstValid()

        email.doAfterTextChanged { signUpStepOneViewModel.onEmailEntered(it.toString()) }
        password.doAfterTextChanged { signUpStepOneViewModel.onPasswordEntered(it.toString()) }

        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.login_dest)
        }
        registered.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.sign_up_to_sign_in_action)
        )
        signUpNext.setOnClickListener {
            signUpViewModel.onGoNextClicked(email.text.toString(), password.text.toString())
            findNavController().navigate(R.id.sign_up_to_step_two_action)
        }
    }

    private fun renderButton(isEnabled: Boolean) {
        signUpNext.isEnabled = isEnabled
    }

    private fun onEvent(event: Event) {
        when (event) {
            is MessageEvent -> showMessage(getString(event.message))
            is ErrorMessageEvent -> showError(getString(event.errorMessage))
        }
    }

    private fun showMessage(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    private fun showError(error: String) {
        Snackbar.make(requireView(), error, Snackbar.LENGTH_SHORT).show()
    }
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
