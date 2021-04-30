package com.workplaces.aslapov.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.activity.BaseActivity

class WelcomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_welcome)

        findViewById<Button>(R.id.goToFeed).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
