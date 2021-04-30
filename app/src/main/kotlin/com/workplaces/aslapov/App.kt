package com.workplaces.aslapov

import android.app.Application
import com.workplaces.aslapov.di.DI
import com.workplaces.aslapov.utils.logging.PrettyLoggingTree
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        DI.init(this)
    }

    private fun initTimber() {
        Timber.plant(PrettyLoggingTree(this))
    }
}
