package com.workplaces.aslapov.app.login.signup

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.datepicker.MaterialDatePicker
import com.redmadrobot.extensions.lifecycle.observe
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.di.DI
import com.workplaces.aslapov.domain.userBirthdayFormatter
import java.util.*
import javax.inject.Inject

class SignUpStepTwoFragment @Inject constructor() : BaseFragment(R.layout.signup_two_fragment) {

    private val signUpViewModel: SignUpViewModel by navGraphViewModels(R.id.sign_up_graph) { viewModelFactory }
    private val signUpStepTwoViewModel: SignUpStepTwoViewModel by viewModels { viewModelFactory }
    private lateinit var register: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firstname = view.findViewById<EditText>(R.id.sign_up_two_firstname)
        val lastname = view.findViewById<EditText>(R.id.sign_up_two_lastname)
        val nickname = view.findViewById<EditText>(R.id.sign_up_two_nickname)
        val birthday = view.findViewById<EditText>(R.id.sign_up_two_birthday)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.sign_up_two_toolbar)
        register = view.findViewById(R.id.sign_up_two_register)
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.sign_up_calendar_title)
            .setSelection(Date().time)
            .build()

        observe(signUpStepTwoViewModel.isRegisterButtonEnabled, ::onRegisterButtonEnableChanged)
        observe(signUpStepTwoViewModel.eventsQueue, ::onEvent)
        observe(signUpViewModel.eventsQueue, ::onEvent)

        firstname.doAfterTextChanged { signUpStepTwoViewModel.onFirstNameEntered(it.toString()) }
        lastname.doAfterTextChanged { signUpStepTwoViewModel.onLastNameEntered(it.toString()) }
        nickname.doAfterTextChanged { signUpStepTwoViewModel.onNickNameEntered(it.toString()) }
        birthday.doAfterTextChanged { signUpStepTwoViewModel.onBirthDayEntered(it.toString()) }
        birthday.setOnClickListener { datePicker.show(parentFragmentManager, "tag") }
        datePicker.addOnPositiveButtonClickListener {
            birthday.text = userBirthdayFormatter.format(Date(it)).toEditable()
        }
        toolbar.setNavigationOnClickListener { signUpStepTwoViewModel.onBackClicked() }
        register.setOnClickListener {
            signUpViewModel.onSignUpClicked(
                firstname = firstname.text.toString(),
                lastname = lastname.text.toString(),
                nickname = nickname.text.toString(),
                birthday = birthday.text.toString()
            )
        }
    }

    private fun onRegisterButtonEnableChanged(isEnabled: Boolean) {
        register.isEnabled = isEnabled
    }
}
