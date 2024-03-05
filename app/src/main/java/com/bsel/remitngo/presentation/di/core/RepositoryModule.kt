package com.bsel.remitngo.presentation.di.core

import com.bsel.remitngo.data.repository.bank.BankRepositoryImpl
import com.bsel.remitngo.data.repository.bank.dataSource.BankRemoteDataSource
import com.bsel.remitngo.data.repository.beneficiary.BeneficiaryRepositoryImpl
import com.bsel.remitngo.data.repository.beneficiary.dataSource.BeneficiaryRemoteDataSource
import com.bsel.remitngo.data.repository.calculation.CalculationRepositoryImpl
import com.bsel.remitngo.data.repository.calculation.dataSource.CalculationRemoteDataSource
import com.bsel.remitngo.data.repository.cancel_request.CancelRequestRepositoryImpl
import com.bsel.remitngo.data.repository.cancel_request.dataSource.CancelRequestRemoteDataSource
import com.bsel.remitngo.data.repository.document.DocumentRepositoryImpl
import com.bsel.remitngo.data.repository.document.dataSource.DocumentRemoteDataSource
import com.bsel.remitngo.data.repository.login.LoginRepositoryImpl
import com.bsel.remitngo.data.repository.login.dataSource.LoginRemoteDataSource
import com.bsel.remitngo.data.repository.payment.PaymentRepositoryImpl
import com.bsel.remitngo.data.repository.payment.dataSource.PaymentRemoteDataSource
import com.bsel.remitngo.data.repository.profile.ProfileRepositoryImpl
import com.bsel.remitngo.data.repository.profile.dataSource.ProfileRemoteDataSource
import com.bsel.remitngo.data.repository.registration.RegistrationRepositoryImpl
import com.bsel.remitngo.data.repository.registration.dataSource.RegistrationRemoteDataSource
import com.bsel.remitngo.data.repository.transaction.TransactionRepositoryImpl
import com.bsel.remitngo.data.repository.transaction.dataSource.TransactionRemoteDataSource
import com.bsel.remitngo.domain.repository.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRegistrationRepository(registrationRemoteDataSource: RegistrationRemoteDataSource): RegistrationRepository {
        return RegistrationRepositoryImpl(registrationRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(loginRemoteDataSource: LoginRemoteDataSource): LoginRepository {
        return LoginRepositoryImpl(loginRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideBeneficiaryRepository(beneficiaryRemoteDataSource: BeneficiaryRemoteDataSource): BeneficiaryRepository {
        return BeneficiaryRepositoryImpl(beneficiaryRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideBankRepository(bankRemoteDataSource: BankRemoteDataSource): BankRepository {
        return BankRepositoryImpl(bankRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideCalculationRepository(calculationRemoteDataSource: CalculationRemoteDataSource): CalculationRepository {
        return CalculationRepositoryImpl(calculationRemoteDataSource)
    }
    @Singleton
    @Provides
    fun providePaymentRepository(paymentRemoteDataSource: PaymentRemoteDataSource): PaymentRepository {
        return PaymentRepositoryImpl(paymentRemoteDataSource)
    }
    @Singleton
    @Provides
    fun provideProfileRepository(profileRemoteDataSource: ProfileRemoteDataSource): ProfileRepository {
        return ProfileRepositoryImpl(profileRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideDocumentRepository(documentRemoteDataSource: DocumentRemoteDataSource): DocumentRepository {
        return DocumentRepositoryImpl(documentRemoteDataSource)
    }
    @Singleton
    @Provides
    fun provideTransactionRepository(transactionRemoteDataSource: TransactionRemoteDataSource): TransactionRepository {
        return TransactionRepositoryImpl(transactionRemoteDataSource)
    }
    @Singleton
    @Provides
    fun provideCancelRequestRepository(cancelRequestRemoteDataSource: CancelRequestRemoteDataSource): CancelRequestRepository {
        return CancelRequestRepositoryImpl(cancelRequestRemoteDataSource)
    }

}
