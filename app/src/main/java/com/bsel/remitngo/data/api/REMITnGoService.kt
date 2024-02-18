package com.bsel.remitngo.data.api

import com.bsel.remitngo.data.model.login.LoginItem
import com.bsel.remitngo.data.model.login.LoginResponseItem
import com.bsel.remitngo.data.model.registration.RegistrationItem
import com.bsel.remitngo.data.model.registration.RegistrationResponseItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface REMITnGoService {
    @POST("api/Home/Registration")
    suspend fun registerUser(@Body registrationItem: RegistrationItem): Response<RegistrationResponseItem>

    @POST("api/Home/Login")
    suspend fun loginUser(@Body loginItem: LoginItem): Response<LoginResponseItem>

}

