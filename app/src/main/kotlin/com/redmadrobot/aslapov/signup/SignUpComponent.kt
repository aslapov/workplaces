package com.redmadrobot.aslapov.signup

import com.redmadrobot.aslapov.di.ActivityScope
import com.redmadrobot.aslapov.signup.firstStep.SignUpStepFirstFragment
import com.redmadrobot.aslapov.signup.secondStep.SignUpStepSecondFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface SignUpComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SignUpComponent
    }

    fun inject(activity: SignUpActivity)
    fun inject(fragment: SignUpStepFirstFragment)
    fun inject(fragment: SignUpStepSecondFragment)
}
