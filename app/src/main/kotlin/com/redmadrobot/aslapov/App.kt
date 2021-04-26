package com.redmadrobot.aslapov

import android.app.Application
import com.redmadrobot.aslapov.utils.logging.PrettyLoggingTree
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        Timber.plant(PrettyLoggingTree(this))
    }
}
