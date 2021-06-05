package com.workplaces.aslapov.app.login.signup

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.redmadrobot.extensions.lifecycle.observe
import com.redmadrobot.extensions.viewbinding.viewBinding
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.databinding.SignupOneFragmentBinding
import com.workplaces.aslapov.di.DI

class SignUpStepOneFragment : BaseFragment(R.layout.signup_one_fragment) {

    private val signUpViewModel: SignUpViewModel by navGraphViewModels(R.id.sign_up_graph) { viewModelFactory }
    private val signUpStepOneViewModel: SignUpStepOneViewModel by viewModels { viewModelFactory }

    private val binding: SignupOneFragmentBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModelObservers()

        binding.apply {
            signUpOneEmail.text = signUpViewModel.email.toEditable()
            signUpOnePassword.text = signUpViewModel.password.toEditable()

            signUpOneEmail.doAfterTextChanged { signUpStepOneViewModel.onEmailEntered(it.toString()) }
            signUpOnePassword.doAfterTextChanged { signUpStepOneViewModel.onPasswordEntered(it.toString()) }

            signUpOneToolbar.setNavigationOnClickListener { signUpStepOneViewModel.onBackClicked() }
            signUpOneRegisterAlready.setOnClickListener { signUpStepOneViewModel.onRegisteredClicked() }

            signUpOneNext.setOnClickListener {
                signUpViewModel.onGoNextClicked(
                    signUpOneEmail.text.toString(),
                    signUpOnePassword.text.toString()
                )
                signUpStepOneViewModel.onNextClicked()
            }

            signUpOneEmail.requestFocus()
        }
    }

    private fun setViewModelObservers() {
        observe(signUpStepOneViewModel.email) { setEditTextError(binding.signUpOneEmail, it) }
        observe(signUpStepOneViewModel.password) { setEditTextError(binding.signUpOnePassword, it) }
        observe(signUpStepOneViewModel.isNextButtonEnabled, ::onNextButtonEnableChanged)
        observe(signUpStepOneViewModel.eventsQueue, ::onEvent)
    }

    private fun onNextButtonEnableChanged(isEnabled: Boolean) {
        binding.signUpOneNext.isEnabled = isEnabled
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
