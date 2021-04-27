package com.redmadrobot.aslapov.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.redmadrobot.aslapov.signup.firstStep.SignUpStepFirstFragment
import com.redmadrobot.aslapov.signup.secondStep.SignUpStepSecondFragment
import com.redmadrobot.aslapov.ui.FragmentsFactory
import com.redmadrobot.aslapov.ui.MainFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FragmentsModule {

    @Binds
    fun bindFragmentFactory(factory: FragmentsFactory): FragmentFactory

    @Binds
    @IntoMap
    @FragmentKey(MainFragment::class)
    fun mainFragment(fragment: MainFragment): Fragment
}
