package com.workplaces.aslapov.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.signin.SignInActivity
import com.workplaces.aslapov.app.signup.SignUpActivity
import com.workplaces.aslapov.app.base.activity.BaseActivity

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
