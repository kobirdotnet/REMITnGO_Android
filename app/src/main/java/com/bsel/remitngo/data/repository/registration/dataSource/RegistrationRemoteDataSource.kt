package com.bsel.remitngo.data.repository.registration.dataSource

import com.bsel.remitngo.data.model.registration.RegistrationItem
import com.bsel.remitngo.data.model.registration.RegistrationResponseItem

import retrofit2.Response

interface RegistrationRemoteDataSource {
    suspend fun registerUser(registrationItem: RegistrationItem): Response<RegistrationResponseItem>
}





