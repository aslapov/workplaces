package com.redmadrobot.aslapov.signup.firstStep

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.snackbar.Snackbar
import com.redmadrobot.aslapov.R
import com.redmadrobot.aslapov.signup.SignUpActivity
import com.redmadrobot.aslapov.signup.SignUpViewModel
import com.redmadrobot.aslapov.ui.base.fragment.BaseFragment
import javax.inject.Inject

class SignUpStepFirstFragment : BaseFragment(R.layout.fragment_signup_first) {

    @Inject
    lateinit var signUpViewModel: SignUpViewModel

    @Inject
    lateinit var signUpStepFirstViewModel: SignUpStepFirstViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as SignUpActivity).signUpComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
            ?: inflater.inflate(R.layout.fragment_signup_first, container, false)
        val signUpActivity = activity as SignUpActivity

        val nickname = view.findViewById<EditText>(R.id.nickname)
        val email = view.findViewById<EditText>(R.id.email)
        val password = view.findViewById<EditText>(R.id.password)
        val signUpNext = view.findViewById<Button>(R.id.signUpNext)

        nickname.text = signUpViewModel.nickname.toEditable()
        email.text = signUpViewModel.email.toEditable()
        password.text = signUpViewModel.password.toEditable()

        email.doAfterTextChanged {
            signUpStepFirstViewModel.validateInput(
                email.text.toString(),
                password.text.toString()
            )
        }
        password.doAfterTextChanged {
            signUpStepFirstViewModel.validateInput(
                email.text.toString(),
                password.text.toString()
            )
        }
        nickname.doAfterTextChanged {
            signUpStepFirstViewModel.validateInput(
                email.text.toString(),
                password.text.toString()
            )
        }

        view.findViewById<Button>(R.id.registerAlready).setOnClickListener {
            signUpActivity.onAlreadyRegister()
        }

        signUpNext.setOnClickListener {
            signUpViewModel.setUserData(
                nickname.text.toString(),
                email.text.toString(),
                password.text.toString()
            )
            signUpActivity.onStepSecond()
        }

        view.findViewById<Button>(R.id.signUpBack).setOnClickListener {
            signUpActivity.onBack()
        }

        signUpStepFirstViewModel.signUpStepFirstViewState.observe(
            signUpActivity,
            { state ->
                when (state) {
                    is SignUpStepFirstSuccess -> {
                        signUpNext.isEnabled = true
                    }
                    is SignUpStepFirstError -> {
                        signUpNext.isEnabled = false
                        Snackbar.make(signUpNext, state.error, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        )

        return view
    }
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
