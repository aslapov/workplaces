package com.workplaces.aslapov.di

import com.workplaces.aslapov.data.profile.FakeUserRepository
import com.workplaces.aslapov.data.profile.UserRepositoryImpl
import com.workplaces.aslapov.domain.UserRepository
import dagger.Binds
import dagger.Module
import javax.inject.Named
import javax.inject.Singleton

@Module
interface AppModule {

    @Singleton
    @Named("Network")
    @Binds
    fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository

    @Singleton
    @Named("Fake")
    @Binds
    fun bindFakeUserRepository(userRepository: FakeUserRepository): UserRepository
}
