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
import javax.inject.Inject

class SignUpStepOneFragment @Inject constructor() : BaseFragment(R.layout.signup_one_fragment) {
    private val signUpViewModel: SignUpViewModel by navGraphViewModels(R.id.sign_up_graph) { viewModelFactory }
    private val signUpStepOneViewModel: SignUpStepOneViewModel by viewModels { viewModelFactory }
    private lateinit var signUpNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(signUpStepOneViewModel.isNextButtonEnabled, ::onNextButtonEnableChanged)
        observe(signUpStepOneViewModel.eventsQueue, ::onEvent)

        val email = view.findViewById<EditText>(R.id.sign_up_one_email)
        val password = view.findViewById<EditText>(R.id.sign_up_one_password)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.sign_up_one_toolbar)
        val registered = view.findViewById<Button>(R.id.sign_up_one_register_already)
        signUpNext = view.findViewById(R.id.sign_up_one_next)

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
    }

    private fun onNextButtonEnableChanged(isEnabled: Boolean) {
        signUpNext.isEnabled = isEnabled
    }
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
