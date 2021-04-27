package com.redmadrobot.aslapov.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.redmadrobot.aslapov.R
import com.redmadrobot.aslapov.signin.SignInActivity
import com.redmadrobot.aslapov.signup.SignUpActivity
import com.redmadrobot.aslapov.ui.base.activity.BaseActivity

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_login)

        setupView()
    }

    private fun setupView() {
        findViewById<Button>(R.id.signIn).setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.signUp).setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
