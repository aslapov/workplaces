package com.workplaces.aslapov.di

import com.workplaces.aslapov.data.UserSharedPreferencesSource
import com.workplaces.aslapov.domain.UserSource
import dagger.Binds
import dagger.Module

@Module
interface StorageModule {

    @Binds
    fun bindUserSource(source: UserSharedPreferencesSource): UserSource
}
