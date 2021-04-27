package com.redmadrobot.aslapov.signup

import com.redmadrobot.aslapov.di.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface SignUpComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SignUpComponent
    }

    fun inject(activity: SignUpActivity)
}
