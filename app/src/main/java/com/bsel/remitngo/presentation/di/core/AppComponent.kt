package com.bsel.remitngo.presentation.di.core

import com.bsel.remitngo.presentation.di.bank.BankSubComponent
import com.bsel.remitngo.presentation.di.beneficiary.BeneficiarySubComponent
import com.bsel.remitngo.presentation.di.calculation.CalculationSubComponent
import com.bsel.remitngo.presentation.di.cancel_request.CancelRequestSubComponent
import com.bsel.remitngo.presentation.di.document.DocumentSubComponent
import com.bsel.remitngo.presentation.di.login.LoginSubComponent
import com.bsel.remitngo.presentation.di.notification.NotificationSubComponent
import com.bsel.remitngo.presentation.di.payment.PaymentSubComponent
import com.bsel.remitngo.presentation.di.profile.ProfileSubComponent
import com.bsel.remitngo.presentation.di.query.QuerySubComponent
import com.bsel.remitngo.presentation.di.registration.RegistrationSubComponent
import com.bsel.remitngo.presentation.di.settings.SettingsSubComponent
import com.bsel.remitngo.presentation.di.support.SupportSubComponent
import com.bsel.remitngo.presentation.di.transaction.TransactionSubComponent
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
    fun bankSubComponent(): BankSubComponent.Factory
    fun calculationSubComponent(): CalculationSubComponent.Factory
    fun paymentSubComponent(): PaymentSubComponent.Factory
    fun profileSubComponent(): ProfileSubComponent.Factory
    fun documentSubComponent(): DocumentSubComponent.Factory
    fun transactionSubComponent(): TransactionSubComponent.Factory
    fun cancelRequestSubComponent(): CancelRequestSubComponent.Factory
    fun querySubComponent(): QuerySubComponent.Factory
    fun settingsSubComponent(): SettingsSubComponent.Factory
    fun supportSubComponent(): SupportSubComponent.Factory
    fun notificationSubComponent(): NotificationSubComponent.Factory
}