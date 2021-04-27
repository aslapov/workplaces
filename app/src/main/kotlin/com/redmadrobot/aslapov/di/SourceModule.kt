package com.redmadrobot.aslapov.di

import com.redmadrobot.aslapov.data.local.UserSharedPreferencesSource
import com.redmadrobot.aslapov.data.local.UserSource
import com.redmadrobot.aslapov.data.remote.AuthApi
import com.redmadrobot.aslapov.data.remote.FakeAuthApiImpl
import com.redmadrobot.aslapov.data.remote.FakeProfileApiImpl
import com.redmadrobot.aslapov.data.remote.ProfileApi
import dagger.Binds
import dagger.Module

@Module
abstract class SourceModule {

    @Binds
    abstract fun provideUserSource(source: UserSharedPreferencesSource): UserSource

    @Binds
    abstract fun provideAuthApi(authApi: FakeAuthApiImpl): AuthApi

    @Binds
    abstract fun provideProfileApi(profileApi: FakeProfileApiImpl): ProfileApi
}
