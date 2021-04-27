package com.redmadrobot.aslapov.di

import com.redmadrobot.aslapov.signin.SignInComponent
import com.redmadrobot.aslapov.signup.SignUpComponent
import com.redmadrobot.aslapov.start.StartComponent
import dagger.Module

@Module(
    subcomponents = [
        StartComponent::class,
        SignInComponent::class,
        SignUpComponent::class
    ]
)
class AppSubcomponents
