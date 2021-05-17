package com.workplaces.aslapov.di

import com.workplaces.aslapov.data.Mock
import com.workplaces.aslapov.data.RepositoryInUse
import com.workplaces.aslapov.data.auth.AuthRepositoryImpl
import com.workplaces.aslapov.data.profile.FakeUserRepository
import com.workplaces.aslapov.data.profile.UserRepositoryImpl
import com.workplaces.aslapov.domain.AuthRepository
import com.workplaces.aslapov.domain.UserRepository
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
interface AppModule {

    @Singleton
    @RepositoryInUse
    @Binds
    fun bindAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Singleton
    @RepositoryInUse
    @Binds
    fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository

    @Singleton
    @Mock
    @Binds
    fun bindFakeUserRepository(userRepository: FakeUserRepository): UserRepository
}
