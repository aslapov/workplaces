package com.workplaces.aslapov.app.signup

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.redmadrobot.extensions.lifecycle.Event
import com.redmadrobot.extensions.lifecycle.observe
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.ErrorMessageEvent
import com.workplaces.aslapov.app.MessageEvent
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SignUpStepTwoFragment @Inject constructor() : BaseFragment(R.layout.signup_two_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val signUpViewModel: SignUpViewModel by viewModels { viewModelFactory }
    private val signUpStepTwoViewModel: SignUpStepTwoViewModel by viewModels { viewModelFactory }
    private lateinit var register: Button
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firstname = view.findViewById<EditText>(R.id.firstname)
        val lastname = view.findViewById<EditText>(R.id.lastname)
        val nickname = view.findViewById<EditText>(R.id.nickname)
        val birthday = view.findViewById<EditText>(R.id.birthday)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        register = view.findViewById(R.id.register)
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.sign_up_calendar_title)
            .setSelection(Date().time)
            .build()

        observe(signUpStepTwoViewModel.isRegisterButtonEnabled, ::renderButton)
        observe(signUpStepTwoViewModel.eventsQueue, ::onEvent)
        observe(signUpViewModel.signUpResult, ::renderResult)

        firstname.doAfterTextChanged { signUpStepTwoViewModel.onFirstNameEntered(it.toString()) }
        lastname.doAfterTextChanged { signUpStepTwoViewModel.onLastNameEntered(it.toString()) }
        nickname.doAfterTextChanged { signUpStepTwoViewModel.onNickNameEntered(it.toString()) }
        birthday.doAfterTextChanged { signUpStepTwoViewModel.onBirthDayEntered(it.toString()) }
        birthday.setOnClickListener { datePicker.show(parentFragmentManager, "tag") }
        datePicker.addOnPositiveButtonClickListener {
            birthday.text = dateFormatter.format(Date(it)).toEditable()
        }
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.sign_up_to_step_one_action)
        }
        register.setOnClickListener {
            signUpViewModel.onSignUpClicked(
                firstname = firstname.text.toString(),
                lastname = lastname.text.toString(),
                nickname = nickname.text.toString(),
                birthday = birthday.text.toString()
            )
        }
    }

    private fun renderButton(isEnabled: Boolean) {
        register.isEnabled = isEnabled
    }
    private fun renderResult(result: SignUpResult) {
        when (result) {
            is SignUpRegistered -> findNavController().navigate(R.id.sign_up_to_welcome_action)
            is SignUpFail -> Snackbar.make(register, result.error, Snackbar.LENGTH_LONG).show()
        }
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
