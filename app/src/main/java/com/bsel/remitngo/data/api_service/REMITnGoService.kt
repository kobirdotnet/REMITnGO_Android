package com.bsel.remitngo.data.api_service

import com.bsel.remitngo.data.model.registration.RegistrationItem
import com.bsel.remitngo.data.model.registration.RegistrationResponseItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface REMITnGoService {
    @POST("api/Home/Registration")
    suspend fun userRegistration(@Body registrationItem: RegistrationItem): Response<RegistrationResponseItem>
}
