package com.redmadrobot.aslapov

import android.app.Application
import com.redmadrobot.aslapov.di.AppComponent
import com.redmadrobot.aslapov.di.DaggerAppComponent
import com.redmadrobot.aslapov.utils.logging.PrettyLoggingTree
import timber.log.Timber

class App : Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }

    private fun initTimber() {
        Timber.plant(PrettyLoggingTree(this))
    }
}
