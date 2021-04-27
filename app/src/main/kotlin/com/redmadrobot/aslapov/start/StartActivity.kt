package com.redmadrobot.aslapov.start

import android.content.Intent
import android.os.Bundle
import com.redmadrobot.aslapov.App
import com.redmadrobot.aslapov.ui.base.activity.BaseActivity
import javax.inject.Inject

class StartActivity : BaseActivity() {

    @Inject
    lateinit var startViewModel: StartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.startComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        showActivity()
    }

    private fun showActivity() {
        val activity = startViewModel.getActivityClass()
        startActivity(Intent(this, activity))
        finish()
    }
}
