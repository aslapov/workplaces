package com.workplaces.aslapov.app.newpost

import androidx.lifecycle.ViewModel
import com.workplaces.aslapov.app.base.viewmodel.ViewModelKey
import com.workplaces.aslapov.app.base.viewmodel.ViewModelModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
interface NewPostModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewPostViewModel::class)
    fun bindNewFeedDummyViewModel(viewModel: NewPostViewModel): ViewModel
}
