package com.workplaces.aslapov.di

import android.content.Context
import com.workplaces.aslapov.App
import com.workplaces.aslapov.app.MainActivity
import com.workplaces.aslapov.app.MainModule
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.app.base.viewmodel.ViewModelModule
import com.workplaces.aslapov.app.feed.FeedModule
import com.workplaces.aslapov.app.login.LoginModule
import com.workplaces.aslapov.app.login.signin.SignInModule
import com.workplaces.aslapov.app.login.signup.SignUpModule
import com.workplaces.aslapov.app.login.welcome.WelcomeModule
import com.workplaces.aslapov.app.newfeed.NewFeedModule
import com.workplaces.aslapov.app.profile.ProfileModule
import com.workplaces.aslapov.data.di.NetworkModule
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
    fun inject(fragment: BaseFragment)
}
