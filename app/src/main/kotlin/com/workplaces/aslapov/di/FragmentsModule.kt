package com.workplaces.aslapov.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.workplaces.aslapov.app.DummyFragment
import com.workplaces.aslapov.app.LoginFragment
import com.workplaces.aslapov.app.WelcomeFragment
import com.workplaces.aslapov.app.base.fragment.FragmentsFactory
import com.workplaces.aslapov.app.signin.SignInFragment
import com.workplaces.aslapov.app.signup.SignUpStepOneFragment
import com.workplaces.aslapov.app.signup.SignUpStepTwoFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FragmentsModule {

    @Binds
    fun bindFragmentFactory(factory: FragmentsFactory): FragmentFactory

    @Binds
    @IntoMap
    @FragmentKey(DummyFragment::class)
    fun dummyFragment(fragment: DummyFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginFragment::class)
    fun loginFragment(fragment: LoginFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SignInFragment::class)
    fun signInFragment(fragment: SignInFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SignUpStepOneFragment::class)
    fun signUpStepFirstFragment(fragment: SignUpStepOneFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SignUpStepTwoFragment::class)
    fun signUpStepSecondFragment(fragment: SignUpStepTwoFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(WelcomeFragment::class)
    fun welcomeFragment(fragment: WelcomeFragment): Fragment
}
