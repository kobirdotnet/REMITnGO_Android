package com.bsel.remitngo.presentation.di.login

import com.bsel.remitngo.domain.useCase.LoginUseCase
import com.bsel.remitngo.presentation.ui.login.LoginViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class LoginModule {
    @LoginScope
    @Provides
    fun provideLoginViewModelFactory(loginUseCase: LoginUseCase): LoginViewModelFactory {
        return LoginViewModelFactory(loginUseCase)
    }
}