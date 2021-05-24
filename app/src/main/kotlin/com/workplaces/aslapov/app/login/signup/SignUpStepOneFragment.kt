package com.workplaces.aslapov.app.login.signup

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.google.android.material.appbar.MaterialToolbar
import com.redmadrobot.extensions.lifecycle.observe
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.di.DI

class SignUpStepOneFragment : BaseFragment(R.layout.signup_one_fragment) {

    private val signUpViewModel: SignUpViewModel by navGraphViewModels(R.id.sign_up_graph) { viewModelFactory }
    private val signUpStepOneViewModel: SignUpStepOneViewModel by viewModels { viewModelFactory }

    private val email: EditText get() = requireView().findViewById(R.id.sign_up_one_email)
    private val password: EditText get() = requireView().findViewById(R.id.sign_up_one_password)
    private val signUpNext: Button get() = requireView().findViewById(R.id.sign_up_one_next)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<MaterialToolbar>(R.id.sign_up_one_toolbar)
        val registered = view.findViewById<Button>(R.id.sign_up_one_register_already)

        setViewModelObservers()

        email.text = signUpViewModel.email.toEditable()
        password.text = signUpViewModel.password.toEditable()

        email.doAfterTextChanged { signUpStepOneViewModel.onEmailEntered(it.toString()) }
        password.doAfterTextChanged { signUpStepOneViewModel.onPasswordEntered(it.toString()) }

        toolbar.setNavigationOnClickListener { signUpStepOneViewModel.onBackClicked() }
        registered.setOnClickListener { signUpStepOneViewModel.onRegisteredClicked() }

        signUpNext.setOnClickListener {
            signUpViewModel.onGoNextClicked(email.text.toString(), password.text.toString())
            signUpStepOneViewModel.onNextClicked()
        }

        email.requestFocus()
    }

    private fun setViewModelObservers() {
        observe(signUpStepOneViewModel.email) { setEditTextError(email, it) }
        observe(signUpStepOneViewModel.password) { setEditTextError(password, it) }
        observe(signUpStepOneViewModel.isNextButtonEnabled, ::onNextButtonEnableChanged)
        observe(signUpStepOneViewModel.eventsQueue, ::onEvent)
    }

    private fun onNextButtonEnableChanged(isEnabled: Boolean) {
        signUpNext.isEnabled = isEnabled
    }

    private fun setEditTextError(editText: EditText, fieldState: SignUpOneFieldState) {
        editText.error = if (fieldState.isValid) {
            null
        } else {
            fieldState.errorId?.let { getString(it) }
        }
    }
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
