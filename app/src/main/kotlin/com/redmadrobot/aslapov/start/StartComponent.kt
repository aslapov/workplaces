package com.redmadrobot.aslapov.start

import com.redmadrobot.aslapov.di.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface StartComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): StartComponent
    }

    fun inject(activity: StartActivity)
}
