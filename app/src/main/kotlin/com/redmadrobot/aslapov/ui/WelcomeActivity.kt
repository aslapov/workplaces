package com.redmadrobot.aslapov.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.redmadrobot.aslapov.R
import com.redmadrobot.aslapov.ui.base.activity.BaseActivity

class WelcomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        findViewById<Button>(R.id.goToFeed).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
