package com.redmadrobot.aslapov.ui

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.commitNow
import com.redmadrobot.aslapov.App
import com.redmadrobot.aslapov.R
import com.redmadrobot.aslapov.start.StartActivity
import com.redmadrobot.aslapov.ui.base.activity.BaseActivity
import com.redmadrobot.aslapov.utils.extension.dispatchApplyWindowInsetsToChild
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        setFragmentFactory()
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<FrameLayout>(R.id.activity_start_container_screens).dispatchApplyWindowInsetsToChild()

        supportFragmentManager.commitNow {
            add(R.id.nav_host_fragment, MainFragment::class.java, null)
        }
    }

    fun onLogout() {
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setFragmentFactory() {
        supportFragmentManager.fragmentFactory = fragmentFactory
    }
}
