package com.redmadrobot.aslapov.di

import android.content.Context
import com.redmadrobot.aslapov.signin.SignInComponent
import com.redmadrobot.aslapov.signup.SignUpComponent
import com.redmadrobot.aslapov.start.StartComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SourceModule::class, AppSubcomponents::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun startComponent(): StartComponent.Factory
    fun signInComponent(): SignInComponent.Factory
    fun signUpComponent(): SignUpComponent.Factory
}
