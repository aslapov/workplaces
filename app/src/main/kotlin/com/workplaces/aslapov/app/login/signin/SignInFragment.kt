package com.workplaces.aslapov.app.login.signin

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.redmadrobot.extensions.lifecycle.observe
import com.redmadrobot.extensions.viewbinding.viewBinding
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.databinding.SigninFragmentBinding
import com.workplaces.aslapov.di.DI

class SignInFragment : BaseFragment(R.layout.signin_fragment) {

    private val signInViewModel: SignInViewModel by viewModels { viewModelFactory }

    private val binding: SigninFragmentBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = binding.signInEmail
        val password = binding.signInPassword

        observe(signInViewModel.email) { setEditTextError(email, it) }
        observe(signInViewModel.password) { setEditTextError(password, it) }
        observe(signInViewModel.isNextButtonEnabled, ::onNextButtonEnabledChanged)
        observe(signInViewModel.isLoading, ::onLoading)
        observe(signInViewModel.eventsQueue, ::onEvent)

        email.doAfterTextChanged { signInViewModel.onEmailEntered(it.toString()) }
        password.doAfterTextChanged { signInViewModel.onPasswordEntered(it.toString()) }

        binding.signInToolbar.setNavigationOnClickListener { signInViewModel.onBackClicked() }
        binding.signInDoRegister.setOnClickListener { signInViewModel.onRegisterClicked() }
        binding.signIn.setOnClickListener { signInViewModel.onSignInClicked() }

        email.requestFocus()
    }

    private fun onNextButtonEnabledChanged(isEnabled: Boolean) {
        binding.signIn.isEnabled = isEnabled
    }

    private fun setEditTextError(editText: EditText, fieldState: SignInFieldState) {
        editText.error = if (fieldState.isValid) {
            null
        } else {
            fieldState.errorId?.let { getString(it) }
        }
    }

    private fun onLoading(isLoading: Boolean) {
        binding.signInProgressLayout.root.isVisible = isLoading
    }
}
