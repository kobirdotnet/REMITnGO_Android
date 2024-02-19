package com.bsel.remitngo.presentation.di.core

import com.bsel.remitngo.presentation.di.beneficiary.BeneficiarySubComponent
import com.bsel.remitngo.presentation.di.login.LoginSubComponent
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
    fun loginSubComponent(): LoginSubComponent.Factory
    fun beneficiarySubComponent(): BeneficiarySubComponent.Factory
}