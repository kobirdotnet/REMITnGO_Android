package com.bsel.remitngo.presentation.di.core

import com.bsel.remitngo.domain.repository.LoginRepository
import com.bsel.remitngo.domain.repository.RegistrationRepository
import com.bsel.remitngo.domain.useCase.LoginUseCase
import com.bsel.remitngo.domain.useCase.RegistrationUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {
    @Provides
    fun provideRegistrationUseCase(registrationRepository: RegistrationRepository): RegistrationUseCase {
        return RegistrationUseCase(registrationRepository)
    }

    @Provides
    fun provideLoginUseCase(loginRepository: LoginRepository): LoginUseCase {
        return LoginUseCase(loginRepository)
    }

}
