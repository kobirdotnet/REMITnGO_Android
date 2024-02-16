package com.bsel.remitngo.data.repository.registration.dataSourceImpl

import com.bsel.remitngo.data.api_service.REMITnGoService
import com.bsel.remitngo.data.model.registration.RegistrationItem
import com.bsel.remitngo.data.model.registration.RegistrationResponseItem
import com.bsel.remitngo.data.repository.registration.dataSource.RegistrationRemoteDataSource
import retrofit2.Response

class RegistrationRemoteDataSourceImpl(private val remitNgoService: REMITnGoService) : RegistrationRemoteDataSource {
    override suspend fun getRegistration(registrationItem: RegistrationItem): Response<RegistrationResponseItem> {
        return remitNgoService.userRegistration(registrationItem)
    }
}
