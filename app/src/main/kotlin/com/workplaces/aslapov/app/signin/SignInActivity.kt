package com.workplaces.aslapov.app.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.LoginActivity
import com.workplaces.aslapov.app.signup.SignUpActivity
import com.workplaces.aslapov.app.WelcomeActivity
import com.workplaces.aslapov.app.base.activity.BaseActivity
import com.workplaces.aslapov.di.DI
import javax.inject.Inject

class SignInActivity : BaseActivity() {

    @Inject
    lateinit var signInViewModel: SignInViewModel

    private lateinit var email: EditText
    private lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        DI.appComponent.signInComponent().create().inject(this)
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_signin)

        setupViews()
    }

    private fun setupViews() {
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        val back = findViewById<Button>(R.id.signInBack)
        val signin = findViewById<Button>(R.id.signInNext)
        val register = findViewById<Button>(R.id.doRegister)

        email.doAfterTextChanged { onUserInfoChanged() }
        password.doAfterTextChanged { onUserInfoChanged() }

        back.setOnClickListener { openActivity(LoginActivity::class.java) }
        register.setOnClickListener { openActivity(SignUpActivity::class.java) }

        signin.setOnClickListener {
            signInViewModel.signIn(
                email.text.toString(),
                password.text.toString()
            )
        }

        signInViewModel.signInFormState.observe(
            this,
            { state ->
                when (state) {
                    is SignInSuccess -> {
                        signin.isEnabled = true
                    }

                    is SignInError -> {
                        signin.isEnabled = false
                        Snackbar.make(register, state.error, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        )

        signInViewModel.signInResult.observe(
            this,
            Observer {
                val signInResult = it ?: return@Observer

                when (signInResult) {
                    is SignInAuthorized -> {
                        val intent = Intent(this, WelcomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    is SignInFail -> {
                        Toast.makeText(this, signInResult.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        )
    }

    private fun onUserInfoChanged() {
        signInViewModel.userInfoChanged(
            email.text.toString(),
            password.text.toString()
        )
    }

    private fun openActivity(activity: Class<out BaseActivity>) {
        val intent = Intent(this, activity)
        startActivity(intent)
        finish()
    }
}
