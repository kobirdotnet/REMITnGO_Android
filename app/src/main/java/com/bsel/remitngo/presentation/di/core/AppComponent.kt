package com.bsel.remitngo.presentation.di.core

import com.bsel.remitngo.presentation.di.registration.RegistrationSubComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetModule::class,
        UseCaseModule::class,
        RepositoryModule::class,
        RemoteDataModule::class
    ]
)
interface AppComponent {
    fun registrationSubComponent(): RegistrationSubComponent.Factory
}