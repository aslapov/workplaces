package com.redmadrobot.aslapov.signin

import com.redmadrobot.aslapov.di.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface SignInComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SignInComponent
    }

    fun inject(activity: SignInActivity)
}
