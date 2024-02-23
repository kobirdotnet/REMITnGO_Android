package com.bsel.remitngo.presentation.di.core

import com.bsel.remitngo.data.repository.bank.BankRepositoryImpl
import com.bsel.remitngo.data.repository.bank.dataSource.BankRemoteDataSource
import com.bsel.remitngo.data.repository.beneficiary.BeneficiaryRepositoryImpl
import com.bsel.remitngo.data.repository.beneficiary.dataSource.BeneficiaryRemoteDataSource
import com.bsel.remitngo.data.repository.calculation.CalculationRepositoryImpl
import com.bsel.remitngo.data.repository.calculation.dataSource.CalculationRemoteDataSource
import com.bsel.remitngo.data.repository.login.LoginRepositoryImpl
import com.bsel.remitngo.data.repository.login.dataSource.LoginRemoteDataSource
import com.bsel.remitngo.data.repository.registration.RegistrationRepositoryImpl
import com.bsel.remitngo.data.repository.registration.dataSource.RegistrationRemoteDataSource
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

}
