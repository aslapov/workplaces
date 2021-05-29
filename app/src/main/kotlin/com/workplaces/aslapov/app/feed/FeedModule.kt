package com.workplaces.aslapov.app.feed

import androidx.lifecycle.ViewModel
import com.workplaces.aslapov.app.base.viewmodel.ViewModelKey
import com.workplaces.aslapov.app.base.viewmodel.ViewModelModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
interface FeedModule {

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    fun bindLoginViewModel(viewModel: FeedViewModel): ViewModel
}
