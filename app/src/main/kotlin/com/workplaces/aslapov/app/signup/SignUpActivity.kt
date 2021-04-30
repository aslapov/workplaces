package com.workplaces.aslapov.app.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.commitNow
import androidx.lifecycle.Observer
import com.workplaces.aslapov.App
import com.redmadrobot.aslapov.R
import com.redmadrobot.aslapov.app.LoginActivity
import com.redmadrobot.aslapov.app.signin.SignInActivity
import com.redmadrobot.aslapov.app.WelcomeActivity
import com.redmadrobot.aslapov.app.base.activity.BaseActivity
import javax.inject.Inject

class SignUpActivity : BaseActivity() {

    @Inject
    lateinit var signUpViewModel: SignUpViewModel

    lateinit var signUpComponent: SignUpComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        signUpComponent = (application as _root_ide_package_.com.workplaces.aslapov.App).appComponent.signUpComponent().create()
        signUpComponent.inject(this)
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        supportFragmentManager.commitNow {
            add(R.id.nav_host_fragment, SignUpStepFirstFragment::class.java, null)
        }

        signUpViewModel.signUpResult.observe(
            this,
            Observer {
                val signUpResult = it ?: return@Observer

                when (signUpResult) {
                    is SignUpAuthorized -> {
                        onWelcome()
                    }

                    is SignUpFail -> {
                        Toast.makeText(this, signUpResult.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        )
    }

    fun onStepSecond() {
        supportFragmentManager.commitNow {
            replace(R.id.nav_host_fragment, SignUpStepSecondFragment::class.java, null)
        }
    }

    fun onWelcome() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun onAlreadyRegister() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun onBack() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun onBackToFirstStep() {
        supportFragmentManager.commitNow {
            replace(R.id.nav_host_fragment, SignUpStepFirstFragment::class.java, null)
        }
    }
}
