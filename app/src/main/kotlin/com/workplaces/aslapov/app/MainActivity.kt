package com.workplaces.aslapov.app

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.commitNow
import com.workplaces.aslapov.R
import com.workplaces.aslapov.di.DI
import com.workplaces.aslapov.app.base.activity.BaseActivity
import com.workplaces.aslapov.utils.extension.dispatchApplyWindowInsetsToChild
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        DI.appComponent.inject(this)
        setFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<FrameLayout>(R.id.activity_start_container_screens).dispatchApplyWindowInsetsToChild()
        val firstFragmentClass = mainViewModel.getFirstFragment()

        supportFragmentManager.commitNow {
            add(firstFragmentClass, null, null)
        }
    }

    private fun setFragmentFactory() {
        supportFragmentManager.fragmentFactory = fragmentFactory
    }
}
