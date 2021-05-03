package com.workplaces.aslapov.di

import android.content.Context
import com.workplaces.aslapov.App
import com.workplaces.aslapov.app.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        FragmentsModule::class,
        ViewModelModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(app: App)
    fun inject(target: MainActivity)
}
