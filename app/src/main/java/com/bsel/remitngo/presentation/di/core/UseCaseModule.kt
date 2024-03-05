package com.bsel.remitngo.presentation.di.core

import com.bsel.remitngo.domain.repository.*
import com.bsel.remitngo.domain.useCase.*
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

    @Provides
    fun provideBeneficiaryUseCase(beneficiaryRepository: BeneficiaryRepository): BeneficiaryUseCase {
        return BeneficiaryUseCase(beneficiaryRepository)
    }

    @Provides
    fun provideBankUseCase(bankRepository: BankRepository): BankUseCase {
        return BankUseCase(bankRepository)
    }

    @Provides
    fun provideCalculationUseCase(calculationRepository: CalculationRepository): CalculationUseCase {
        return CalculationUseCase(calculationRepository)
    }
    @Provides
    fun providePaymentUseCase(paymentRepository: PaymentRepository): PaymentUseCase {
        return PaymentUseCase(paymentRepository)
    }
    @Provides
    fun provideProfileUseCase(profileRepository: ProfileRepository): ProfileUseCase {
        return ProfileUseCase(profileRepository)
    }
    @Provides
    fun provideDocumentUseCase(documentRepository: DocumentRepository): DocumentUseCase {
        return DocumentUseCase(documentRepository)
    }
    @Provides
    fun provideTransactionUseCase(transactionRepository: TransactionRepository): TransactionUseCase {
        return TransactionUseCase(transactionRepository)
    }
    @Provides
    fun provideCancelRequestUseCase(cancelRequestRepository: CancelRequestRepository): CancelRequestUseCase {
        return CancelRequestUseCase(cancelRequestRepository)
    }

}
