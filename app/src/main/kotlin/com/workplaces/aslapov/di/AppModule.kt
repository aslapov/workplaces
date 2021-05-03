package com.workplaces.aslapov.di

import com.workplaces.aslapov.data.UserRepositoryImpl
import com.workplaces.aslapov.domain.UserRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface AppModule {
    @Singleton
    @Binds
    fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository
}
