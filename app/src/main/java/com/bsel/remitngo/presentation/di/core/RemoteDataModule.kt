package com.bsel.remitngo.presentation.di.core

import com.bsel.remitngo.data.api.REMITnGoService
import com.bsel.remitngo.data.repository.bank.dataSource.BankRemoteDataSource
import com.bsel.remitngo.data.repository.bank.dataSourceImpl.BankRemoteDataSourceImpl
import com.bsel.remitngo.data.repository.beneficiary.dataSource.BeneficiaryRemoteDataSource
import com.bsel.remitngo.data.repository.beneficiary.dataSourceImpl.BeneficiaryRemoteDataSourceImpl
import com.bsel.remitngo.data.repository.calculation.dataSource.CalculationRemoteDataSource
import com.bsel.remitngo.data.repository.calculation.dataSourceImpl.CalculationRemoteDataSourceImpl
import com.bsel.remitngo.data.repository.cancel_request.dataSource.CancelRequestRemoteDataSource
import com.bsel.remitngo.data.repository.cancel_request.dataSourceImpl.CancelRequestRemoteDataSourceImpl
import com.bsel.remitngo.data.repository.document.dataSource.DocumentRemoteDataSource
import com.bsel.remitngo.data.repository.document.dataSourceImpl.DocumentRemoteDataSourceImpl
import com.bsel.remitngo.data.repository.login.dataSource.LoginRemoteDataSource
import com.bsel.remitngo.data.repository.login.dataSourceImpl.LoginRemoteDataSourceImpl
import com.bsel.remitngo.data.repository.payment.dataSource.PaymentRemoteDataSource
import com.bsel.remitngo.data.repository.payment.dataSourceImpl.PaymentRemoteDataSourceImpl
import com.bsel.remitngo.data.repository.profile.dataSource.ProfileRemoteDataSource
import com.bsel.remitngo.data.repository.profile.dataSourceImpl.ProfileRemoteDataSourceImpl
import com.bsel.remitngo.data.repository.registration.dataSource.RegistrationRemoteDataSource
import com.bsel.remitngo.data.repository.registration.dataSourceImpl.RegistrationRemoteDataSourceImpl
import com.bsel.remitngo.data.repository.transaction.dataSource.TransactionRemoteDataSource
import com.bsel.remitngo.data.repository.transaction.dataSourceImpl.TransactionRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataModule {
    @Singleton
    @Provides
    fun provideRegistrationRemoteDataSource(remitNgoService: REMITnGoService): RegistrationRemoteDataSource {
        return RegistrationRemoteDataSourceImpl(remitNgoService)
    }

    @Singleton
    @Provides
    fun provideLoginRemoteDataSource(remitNgoService: REMITnGoService): LoginRemoteDataSource {
        return LoginRemoteDataSourceImpl(remitNgoService)
    }

    @Singleton
    @Provides
    fun provideBeneficiaryRemoteDataSource(remitNgoService: REMITnGoService): BeneficiaryRemoteDataSource {
        return BeneficiaryRemoteDataSourceImpl(remitNgoService)
    }

    @Singleton
    @Provides
    fun provideBankRemoteDataSource(remitNgoService: REMITnGoService): BankRemoteDataSource {
        return BankRemoteDataSourceImpl(remitNgoService)
    }

    @Singleton
    @Provides
    fun provideCalculationRemoteDataSource(remitNgoService: REMITnGoService): CalculationRemoteDataSource {
        return CalculationRemoteDataSourceImpl(remitNgoService)
    }
    @Singleton
    @Provides
    fun providePaymentRemoteDataSource(remitNgoService: REMITnGoService): PaymentRemoteDataSource {
        return PaymentRemoteDataSourceImpl(remitNgoService)
    }
    @Singleton
    @Provides
    fun provideProfileRemoteDataSource(remitNgoService: REMITnGoService): ProfileRemoteDataSource {
        return ProfileRemoteDataSourceImpl(remitNgoService)
    }
    @Singleton
    @Provides
    fun provideDocumentRemoteDataSource(remitNgoService: REMITnGoService): DocumentRemoteDataSource {
        return DocumentRemoteDataSourceImpl(remitNgoService)
    }

    @Singleton
    @Provides
    fun provideTransactionRemoteDataSource(remitNgoService: REMITnGoService): TransactionRemoteDataSource {
        return TransactionRemoteDataSourceImpl(remitNgoService)
    }

    @Singleton
    @Provides
    fun provideCancelRequestRemoteDataSource(remitNgoService: REMITnGoService): CancelRequestRemoteDataSource {
        return CancelRequestRemoteDataSourceImpl(remitNgoService)
    }

}
