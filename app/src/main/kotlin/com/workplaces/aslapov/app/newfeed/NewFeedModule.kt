package com.workplaces.aslapov.app.newfeed

import androidx.lifecycle.ViewModel
import com.workplaces.aslapov.app.base.viewmodel.ViewModelKey
import com.workplaces.aslapov.di.ViewModelModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
interface NewFeedModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewFeedDummyViewModel::class)
    fun bindNewFeedDummyViewModel(viewModel: NewFeedDummyViewModel): ViewModel
}
