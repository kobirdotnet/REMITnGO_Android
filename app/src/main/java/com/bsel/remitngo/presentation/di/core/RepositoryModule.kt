package com.bsel.remitngo.presentation.di.core

import com.bsel.remitngo.data.repository.registration.RegistrationRepositoryImpl
import com.bsel.remitngo.data.repository.registration.dataSource.RegistrationRemoteDataSource
import com.bsel.remitngo.data.repository.registration.dataSourceImpl.RegistrationRemoteDataSourceImpl
import com.bsel.remitngo.domain.repository.RegistrationRepository
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
}
