package com.workplaces.aslapov.di

import android.content.Context
import com.workplaces.aslapov.App
import com.workplaces.aslapov.app.MainActivity
import com.workplaces.aslapov.app.MainModule
import com.workplaces.aslapov.app.base.viewmodel.ViewModelModule
import com.workplaces.aslapov.app.feed.FeedModule
import com.workplaces.aslapov.app.login.LoginFragment
import com.workplaces.aslapov.app.login.LoginModule
import com.workplaces.aslapov.app.login.signin.SignInFragment
import com.workplaces.aslapov.app.login.signin.SignInModule
import com.workplaces.aslapov.app.login.signup.SignUpFlowFragment
import com.workplaces.aslapov.app.login.signup.SignUpModule
import com.workplaces.aslapov.app.login.signup.SignUpStepOneFragment
import com.workplaces.aslapov.app.login.signup.SignUpStepTwoFragment
import com.workplaces.aslapov.app.login.welcome.WelcomeFragment
import com.workplaces.aslapov.app.login.welcome.WelcomeModule
import com.workplaces.aslapov.app.newfeed.NewFeedDummyFragment
import com.workplaces.aslapov.app.newfeed.NewFeedModule
import com.workplaces.aslapov.app.profile.ProfileModule
import com.workplaces.aslapov.data.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        MainModule::class,
        LoginModule::class,
        SignInModule::class,
        SignUpModule::class,
        WelcomeModule::class,
        FeedModule::class,
        NewFeedModule::class,
        ProfileModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(app: App)
    fun inject(target: MainActivity)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: SignInFragment)
    fun inject(fragment: SignUpFlowFragment)
    fun inject(fragment: SignUpStepOneFragment)
    fun inject(fragment: SignUpStepTwoFragment)
    fun inject(fragment: WelcomeFragment)
    fun inject(fragment: NewFeedDummyFragment)
}
