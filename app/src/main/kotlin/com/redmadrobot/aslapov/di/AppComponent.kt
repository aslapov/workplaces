package com.redmadrobot.aslapov.di

import android.content.Context
import com.redmadrobot.aslapov.signin.SignInComponent
import com.redmadrobot.aslapov.signup.SignUpComponent
import com.redmadrobot.aslapov.start.StartComponent
import com.redmadrobot.aslapov.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FragmentsModule::class, SourceModule::class, AppSubcomponents::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun startComponent(): StartComponent.Factory
    fun signInComponent(): SignInComponent.Factory
    fun signUpComponent(): SignUpComponent.Factory

    fun inject(target: MainActivity)
}
