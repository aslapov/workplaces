package com.redmadrobot.aslapov.ui

import android.os.Bundle
import android.widget.FrameLayout
import com.redmadrobot.aslapov.R
import com.redmadrobot.aslapov.ui.base.activity.BaseActivity
import com.redmadrobot.aslapov.utils.extension.dispatchApplyWindowInsetsToChild

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<FrameLayout>(R.id.activity_start_container_screens).dispatchApplyWindowInsetsToChild()
    }
}
