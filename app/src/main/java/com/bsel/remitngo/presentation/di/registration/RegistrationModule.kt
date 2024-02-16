package com.bsel.remitngo.presentation.di.registration

import com.bsel.remitngo.domain.useCase.RegistrationUseCase
import com.bsel.remitngo.presentation.registration.RegistrationViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class RegistrationModule {
    @RegistrationScope
    @Provides
    fun provideRegistrationViewModelFactory(registrationUseCase: RegistrationUseCase): RegistrationViewModelFactory {
        return RegistrationViewModelFactory(registrationUseCase)
    }
}