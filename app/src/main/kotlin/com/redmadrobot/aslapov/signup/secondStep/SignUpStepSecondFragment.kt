package com.redmadrobot.aslapov.signup.secondStep

import android.content.Context
import android.os.Bundle
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

class SignUpStepSecondFragment : BaseFragment(R.layout.fragment_signup_second) {

    @Inject
    lateinit var signUpViewModel: SignUpViewModel

    @Inject
    lateinit var signUpStepSecondViewModel: SignUpStepSecondViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as SignUpActivity).signUpComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
            ?: inflater.inflate(R.layout.fragment_signup_first, container, false)
        val signUpActivity = activity as SignUpActivity

        val firstname = view.findViewById<EditText>(R.id.firstname)
        val lastname = view.findViewById<EditText>(R.id.lastname)
        val birthday = view.findViewById<EditText>(R.id.birthday)
        val register = view.findViewById<Button>(R.id.register)

        firstname.doAfterTextChanged {
            signUpStepSecondViewModel.validateInput(
                firstname.text.toString(),
                lastname.text.toString(),
                birthday.text.toString()
            )
        }
        lastname.doAfterTextChanged {
            signUpStepSecondViewModel.validateInput(
                firstname.text.toString(),
                lastname.text.toString(),
                birthday.text.toString()
            )
        }
        birthday.doAfterTextChanged {
            signUpStepSecondViewModel.validateInput(
                firstname.text.toString(),
                lastname.text.toString(),
                birthday.text.toString()
            )
        }

        register.setOnClickListener {
            signUpViewModel.updateUserData(
                firstname.text.toString(),
                lastname.text.toString(),
                birthday.text.toString()
            )
            signUpViewModel.signUp()
        }

        view.findViewById<Button>(R.id.signUpBack).setOnClickListener {
            signUpActivity.onBackToFirstStep()
        }

        signUpStepSecondViewModel.signUpStepSecondViewState.observe(
            signUpActivity,
            { state ->
                when (state) {
                    is SignUpStepSecondSuccess -> {
                        register.isEnabled = true
                    }
                    is SignUpStepSecondError -> {
                        register.isEnabled = false
                        Snackbar.make(register, state.error, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        )

        return view
    }
}
