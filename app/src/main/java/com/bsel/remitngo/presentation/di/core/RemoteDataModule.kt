package com.bsel.remitngo.presentation.di.core

import com.bsel.remitngo.data.api_service.REMITnGoService
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
}
