package com.workplaces.aslapov.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.workplaces.aslapov.app.DummyViewModel
import com.workplaces.aslapov.app.MainViewModel
import com.workplaces.aslapov.app.base.viewmodel.ViewModelFactory
import com.workplaces.aslapov.app.signin.SignInViewModel
import com.workplaces.aslapov.app.signup.SignUpStepOneViewModel
import com.workplaces.aslapov.app.signup.SignUpStepTwoViewModel
import com.workplaces.aslapov.app.signup.SignUpViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    fun bindSignInViewModel(viewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    fun bindSignUpViewModel(viewModel: SignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpStepOneViewModel::class)
    fun bindSignUpStepFirstViewModel(viewModel: SignUpStepOneViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpStepTwoViewModel::class)
    fun bindSignUpSignUpStepSecondViewModel(viewModel: SignUpStepTwoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DummyViewModel::class)
    fun bindDummyViewModel(viewModel: DummyViewModel): ViewModel
}
