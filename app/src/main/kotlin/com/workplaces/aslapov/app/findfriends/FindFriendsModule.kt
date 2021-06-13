package com.workplaces.aslapov.app.findfriends

import androidx.lifecycle.ViewModel
import com.workplaces.aslapov.app.base.viewmodel.ViewModelKey
import com.workplaces.aslapov.app.base.viewmodel.ViewModelModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
interface FindFriendsModule {

    @Binds
    @IntoMap
    @ViewModelKey(FindFriendsViewModel::class)
    fun bindFindFriendsViewModel(viewModel: FindFriendsViewModel): ViewModel
}
