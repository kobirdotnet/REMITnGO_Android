package com.bsel.remitngo.presentation.di.core

import com.bsel.remitngo.data.api.REMITnGoService
import com.bsel.remitngo.data.repository.beneficiary.dataSource.BeneficiaryRemoteDataSource
import com.bsel.remitngo.data.repository.beneficiary.dataSourceImpl.BeneficiaryRemoteDataSourceImpl
import com.bsel.remitngo.data.repository.login.dataSource.LoginRemoteDataSource
import com.bsel.remitngo.data.repository.login.dataSourceImpl.LoginRemoteDataSourceImpl
import com.bsel.remitngo.data.repository.registration.dataSource.RegistrationRemoteDataSource
import com.bsel.remitngo.data.repository.registration.dataSourceImpl.RegistrationRemoteDataSourceImpl
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

}
