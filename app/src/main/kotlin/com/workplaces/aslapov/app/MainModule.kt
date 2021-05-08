package com.workplaces.aslapov.app

import androidx.lifecycle.ViewModel
import com.workplaces.aslapov.app.base.viewmodel.ViewModelKey
import com.workplaces.aslapov.di.ViewModelModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
interface MainModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}
