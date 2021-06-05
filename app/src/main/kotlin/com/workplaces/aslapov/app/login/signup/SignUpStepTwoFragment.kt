package com.workplaces.aslapov.app.login.signup

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.redmadrobot.extensions.lifecycle.observe
import com.redmadrobot.extensions.viewbinding.viewBinding
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.data.util.helpers.convertToLocalDateViaInstant
import com.workplaces.aslapov.databinding.SignupTwoFragmentBinding
import com.workplaces.aslapov.di.DI
import com.workplaces.aslapov.domain.util.dateTimeFormatter
import java.util.*

class SignUpStepTwoFragment : BaseFragment(R.layout.signup_two_fragment) {

    private val signUpViewModel: SignUpViewModel by navGraphViewModels(R.id.sign_up_graph) { viewModelFactory }
    private val signUpStepTwoViewModel: SignUpStepTwoViewModel by viewModels { viewModelFactory }

    private val binding: SignupTwoFragmentBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModelObservers()
        setEditTextWatchers()

        binding.apply {
            signUpTwoBirthday.setOnClickListener { showDatePicker() }
            signUpTwoToolbar.setNavigationOnClickListener { signUpStepTwoViewModel.onBackClicked() }

            signUpTwoRegister.setOnClickListener {
                signUpViewModel.onSignUpClicked(
                    firstname = signUpTwoFirstname.text.toString(),
                    lastname = signUpTwoLastname.text.toString(),
                    nickname = signUpTwoNickname.text.toString(),
                    birthday = signUpTwoBirthday.text.toString()
                )
            }

            signUpTwoFirstname.requestFocus()
        }
    }

    private fun setViewModelObservers() {
        observe(signUpStepTwoViewModel.firstName) { setEditTextError(binding.signUpTwoFirstname, it) }
        observe(signUpStepTwoViewModel.lastName) { setEditTextError(binding.signUpTwoLastname, it) }
        observe(signUpStepTwoViewModel.nickName) { setEditTextError(binding.signUpTwoNickname, it) }
        observe(signUpStepTwoViewModel.birthDay) { setEditTextError(binding.signUpTwoBirthday, it) }
        observe(signUpStepTwoViewModel.isRegisterButtonEnabled, ::onRegisterButtonEnableChanged)
        observe(signUpStepTwoViewModel.eventsQueue, ::onEvent)
        observe(signUpViewModel.isLoading, ::onLoading)
        observe(signUpViewModel.eventsQueue, ::onEvent)
    }

    private fun setEditTextWatchers() {
        binding.apply {
            signUpTwoFirstname.doAfterTextChanged { signUpStepTwoViewModel.onFirstNameEntered(it.toString()) }
            signUpTwoLastname.doAfterTextChanged { signUpStepTwoViewModel.onLastNameEntered(it.toString()) }
            signUpTwoNickname.doAfterTextChanged { signUpStepTwoViewModel.onNickNameEntered(it.toString()) }
            signUpTwoBirthday.doAfterTextChanged { signUpStepTwoViewModel.onBirthDayEntered(it.toString()) }
        }
    }

    private fun onRegisterButtonEnableChanged(isEnabled: Boolean) {
        binding.signUpTwoRegister.isEnabled = isEnabled
    }

    private fun setEditTextError(editText: EditText, fieldState: SignUpTwoFieldState) {
        editText.error = if (fieldState.isValid) {
            null
        } else {
            fieldState.errorId?.let { getString(it) }
        }
    }

    private fun onLoading(isLoading: Boolean) {
        binding.signUpProgressLayout.root.isVisible = isLoading
    }

    private fun showDatePicker() {
        MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.sign_up_calendar_title)
            .setSelection(Date().time)
            .build()
            .apply {
                addOnPositiveButtonClickListener {
                    binding.signUpTwoBirthday.text = Date(it).convertToLocalDateViaInstant()
                        .format(dateTimeFormatter)
                        .toEditable()
                }
            }
            .show(parentFragmentManager, "tag")
    }
}
