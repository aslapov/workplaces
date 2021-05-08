package com.workplaces.aslapov.app.login.welcome

import androidx.lifecycle.ViewModel
import com.workplaces.aslapov.app.base.viewmodel.ViewModelKey
import com.workplaces.aslapov.di.ViewModelModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
interface WelcomeModule {

    @Binds
    @IntoMap
    @ViewModelKey(WelcomeViewModel::class)
    fun bindWelcomeViewModel(viewModel: WelcomeViewModel): ViewModel
}
