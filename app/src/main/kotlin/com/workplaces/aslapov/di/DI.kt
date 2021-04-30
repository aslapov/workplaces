package com.workplaces.aslapov.di

import android.app.Application

object DI {
    lateinit var appComponent: AppComponent

    fun init(application: Application) {
        appComponent = DaggerAppComponent
            .factory()
            .create(application)
    }
}
