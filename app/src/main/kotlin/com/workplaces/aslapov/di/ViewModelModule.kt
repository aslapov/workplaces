package com.workplaces.aslapov.di

import androidx.lifecycle.ViewModelProvider
import com.workplaces.aslapov.app.base.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
