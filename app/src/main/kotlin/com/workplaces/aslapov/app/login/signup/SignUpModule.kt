package com.workplaces.aslapov.app.login.signup

import androidx.lifecycle.ViewModel
import com.workplaces.aslapov.app.base.viewmodel.ViewModelKey
import com.workplaces.aslapov.app.base.viewmodel.ViewModelModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
interface SignUpModule {

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
}
