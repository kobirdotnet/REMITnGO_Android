package com.bsel.remitngo.presentation

import android.app.Application
import com.bsel.remitngo.BuildConfig
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.di.bank.BankSubComponent
import com.bsel.remitngo.presentation.di.beneficiary.BeneficiarySubComponent
import com.bsel.remitngo.presentation.di.calculation.CalculationSubComponent
import com.bsel.remitngo.presentation.di.cancel_request.CancelRequestSubComponent
import com.bsel.remitngo.presentation.di.core.*
import com.bsel.remitngo.presentation.di.document.DocumentSubComponent
import com.bsel.remitngo.presentation.di.login.LoginSubComponent
import com.bsel.remitngo.presentation.di.payment.PaymentSubComponent
import com.bsel.remitngo.presentation.di.profile.ProfileSubComponent
import com.bsel.remitngo.presentation.di.registration.RegistrationSubComponent
import com.bsel.remitngo.presentation.di.transaction.TransactionSubComponent

class App : Application(), Injector {
    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .netModule(NetModule(BuildConfig.BASE_URL))
            .useCaseModule(UseCaseModule())
            .repositoryModule(RepositoryModule())
            .remoteDataModule(RemoteDataModule())
            .build()
    }

    override fun createRegistrationSubComponent(): RegistrationSubComponent {
        return appComponent.registrationSubComponent().create()
    }

    override fun createLoginSubComponent(): LoginSubComponent {
        return appComponent.loginSubComponent().create()
    }

    override fun createBeneficiarySubComponent(): BeneficiarySubComponent {
        return appComponent.beneficiarySubComponent().create()
    }

    override fun createBankSubComponent(): BankSubComponent {
        return appComponent.bankSubComponent().create()
    }

    override fun createCalculationSubComponent(): CalculationSubComponent {
        return appComponent.calculationSubComponent().create()
    }
    override fun createPaymentSubComponent(): PaymentSubComponent {
        return appComponent.paymentSubComponent().create()
    }
    override fun createProfileSubComponent(): ProfileSubComponent {
        return appComponent.profileSubComponent().create()
    }

    override fun createDocumentSubComponent(): DocumentSubComponent {
        return appComponent.documentSubComponent().create()
    }
    override fun createTransactionSubComponent(): TransactionSubComponent {
        return appComponent.transactionSubComponent().create()
    }

    override fun createCancelRequestSubComponent(): CancelRequestSubComponent {
        return appComponent.cancelRequestSubComponent().create()
    }

}
