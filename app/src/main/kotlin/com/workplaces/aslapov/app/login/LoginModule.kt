package com.workplaces.aslapov.app.login

import androidx.lifecycle.ViewModel
import com.workplaces.aslapov.app.base.viewmodel.ViewModelKey
import com.workplaces.aslapov.app.base.viewmodel.ViewModelModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
interface LoginModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel
}
